package scalasmplmsgq.controller

import org.slf4j.LoggerFactory
import play.api.mvc.{Action, Controller}
import play.libs.Json

import scalasmplmsgq.data.model.{InMemory, Que, QueueCreateRequest}
import scalasmplmsgq.exceptions.ConsumerNotFoundException

/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
class Application extends Controller {

  val logger = LoggerFactory.getLogger(getClass)

  def index = Action {
    val json = Json.toJson("this is a test")
    Ok(json.asText())
  }

  def createQueue = Action(parse.json[QueueCreateRequest]) {
    request => {
      val qRequest: QueueCreateRequest = request.body
      if (qRequest.name.isEmpty) {
        BadRequest("Queue name is empty.")
      } else
      if (InMemory.queueMap.contains(qRequest.name)) {
        Conflict("Queue already exists.")
      } else {
        val que: Que = new Que(qRequest.consumers)
        InMemory.queueMap.put(qRequest.name, que)
        Created("Queue created.")
      }
    }
  }

  def notFound = Action {
    NotFound
  }

  def registerConsumerToQueue = TODO

  def enqueue(qName: String) = Action(parse.anyContent) {
    request => {
      if(qName.isEmpty) {
        BadRequest("Queue name is empty.")
      } else if(request.body.asText.isEmpty) {
        BadRequest("Message is empty.")
      } else if (!InMemory.queueMap.contains(qName)) {
        BadRequest("Queue dose not exists.")
      } else {
        val q:Que = InMemory.queueMap.get(qName).get
        q.enqueue(request.body.asText.get)
        Created("Message in queue.")
      }
    }
  }

  def dequeue(qName: String,cid: String) = Action(parse.anyContent) {
    request => {
      if (qName.isEmpty) {
        BadRequest("Qname is empty!!!")
      } else if (cid.isEmpty) {
        BadRequest("Consumer is empty!!!")
      } else if (!InMemory.queueMap.contains(qName)) {
        BadRequest("Queue dose not exists.")
      } else {
        try {
          val q: Que = InMemory.queueMap.get(qName).get
          val msg: String = q.dequeue(cid)
          if (msg == null)
            Ok("null")
          else
            Ok(msg)
        } catch {
          case e:ConsumerNotFoundException =>
            BadRequest(cid + " not registered to queue "+ qName)
        }
      }
    }
  }
}
