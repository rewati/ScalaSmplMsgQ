package scalasmplmsgq.data.model

/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
class DataNode(data: String) {
  var next: DataNode = null
  def setNext(dataNode: DataNode): Unit = {
    next = dataNode
  }
  def getData() = data
}
