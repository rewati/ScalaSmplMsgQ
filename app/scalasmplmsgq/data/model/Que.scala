package scalasmplmsgq.data.model

import scala.collection.mutable
import scalasmplmsgq.exceptions.{ConsumerCollisionException, ConsumerNotFoundException}

/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
case class Que(consumers: List[String]) {
  private val consumerMap = new mutable.HashMap[String,DataNode]
  private var consumerWithEmptyQ:mutable.HashSet[String] = new mutable.HashSet[String]()
  consumers.foreach(c => {
    consumerWithEmptyQ.add(c)
    consumerMap.put(c,null)
  })
  private var first:DataNode = null
  private var last:DataNode = null

  def enqueue(msg: String): Unit = {
    val dataNode = new DataNode(msg)
    if(first==null) {
      first = dataNode
    }
      else last.setNext(dataNode)
    last = dataNode
    consumerWithEmptyQ.foreach(eq => consumerMap.put(eq,last))
    consumerWithEmptyQ = new mutable.HashSet[String]()
  }

  def dequeue(consumerName: String): String = {
    if(!consumerMap.contains(consumerName))
      throw ConsumerNotFoundException("Consumer "+consumerName+" dose not exists!!")
    val node = consumerMap(consumerName)
    if(node == null)
      return null
    val msg = node.getData()
    consumerMap.put(consumerName,node.next)
    if(node.next == null) consumerWithEmptyQ.add(consumerName)
    msg
  }

  def addConsumer(consumerName: String) = {
    try {
      consumerMap(consumerName)
      throw ConsumerCollisionException("Consumer "+consumerName+" already exists!!")
    } catch {
      case e:NoSuchElementException => consumerMap.put(consumerName,first)
    }
  }
}

object Que
