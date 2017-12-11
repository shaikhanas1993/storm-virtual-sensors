package com.smartstorm.api

case class Message (
                     user_id: String,
                     sensor_id: String,
                     desc: String,
                     measure_value: String
                   )