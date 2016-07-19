package scalasmplmsgq.data.model

import scala.collection.mutable

/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
object InMemory {
  val queueMap = new mutable.HashMap[String,Que]
}
