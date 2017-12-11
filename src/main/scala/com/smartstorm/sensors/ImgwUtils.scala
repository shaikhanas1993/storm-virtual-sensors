package com.smartstorm.sensors

import com.smartstorm.api.{ApiUtils, Message}
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

object ImgwUtils {
  private val logger = Logger.getLogger(getClass)
  private implicit val formats = DefaultFormats

  def getLastMeasure(): Imgw = {

    val httpClient = HttpClientBuilder.create().build()
    val response = httpClient.execute(new HttpGet("https://danepubliczne.imgw.pl/api/data/synop/station/gdansk"))

    val entity = response.getEntity
    val inputStream = entity.getContent
    val content = io.Source.fromInputStream(inputStream).getLines.mkString
    inputStream.close

    httpClient.close()
    logger.info(content)

    val imgwData = parse(content).extract[Imgw]

    imgwData
  }

  def sendData(imgw: Imgw): Unit = {
    val msg = new Message(user_id = "test123","abc123","IMGW",imgw.temperatura)
    ApiUtils.sendMessage(msg)
  }

}

case class Imgw (
                  id_stacji: String,
                  stacja: String,
                  data_pomiaru: String,
                  godzina_pomiaru: String,
                  temperatura: String,
                  predkosc_wiatru: String,
                  kierunek_wiatru: String,
                  wilgotnosc_wzgledna: String,
                  odparowanie: Option[String],
                  suma_opadu: String,
                  cisnienie: String
                )
