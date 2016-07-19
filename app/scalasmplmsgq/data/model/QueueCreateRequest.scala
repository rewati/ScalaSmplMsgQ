package scalasmplmsgq.data.model

import play.api.libs.json.Json


/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
case class QueueCreateRequest(name:String,consumers:List[String])
object QueueCreateRequest{
  implicit val format = Json.format[QueueCreateRequest]
}
