package scalasmplmsgq.exceptions

/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
case class ConsumerCollisionException(explain:String)  extends Exception(explain)

case class ConsumerNotFoundException(explain:String)  extends Exception(explain)
