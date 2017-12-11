package com.smartstorm.api

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.log4j.Logger
import org.json4s.DefaultFormats

object ApiUtils {
  private val logger = Logger.getLogger(getClass)
  private implicit val formats = DefaultFormats

  def sendMessage(message: Message): Unit = {
    val httpClient = HttpClientBuilder.create().build()
    val post = new HttpPost("http://alfa.smartstorm.io/api/v1/measure")
    post.setHeader("Content-type", "application/json")

    val messageJson = org.json4s.native.Serialization.write(message)
    post.setEntity(new StringEntity(messageJson))
    logger.info(s"sending message = ${messageJson}")

    val response = httpClient.execute(post)
    val responseCode = response.getStatusLine.getStatusCode

    logger.info(s"response status code = ${responseCode}")
  }
}
