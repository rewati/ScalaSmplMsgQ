package scalasmplmsgq.data.model

import org.scalatest.FunSuite

import scalasmplmsgq.exceptions.ConsumerNotFoundException

/**
  * Created by Rewati Raman(rewati.raman@gmail.com).
  */
class QueTest extends FunSuite {

  test("Single Consumer Enqueue and Dequeue") {
    val consumer = "test"
    val s: Que = new Que(List(consumer))
    assert(s.dequeue(consumer) == null)
    s.enqueue("1")
    s.enqueue("2")
    assert(s.dequeue(consumer) == "1")
    s.enqueue("3")
    assert(s.dequeue(consumer) == "2")
    assert(s.dequeue(consumer) == "3")
    assert(s.dequeue(consumer) == null)
  }

  test("Multiple Consumer test") {
    val consumer = "test"
    val s: Que = new Que(List(consumer))
    assert(s.dequeue(consumer) == null)
    s.enqueue("1")
    s.enqueue("2")
    assert(s.dequeue(consumer) == "1")
    s.enqueue("3")
    assert(s.dequeue(consumer) == "2")
    val consumer2 = "test2"
    s.addConsumer(consumer2)
    assert(s.dequeue(consumer2) == "1")
    assert(s.dequeue(consumer2) == "2")
    assert(s.dequeue(consumer2) == "3")
    assert(s.dequeue(consumer) == "3")
    assert(s.dequeue(consumer) == null)

  }

  test("Multiple Consumer empty q test ") {
    val consumer1 = "test1"
    val consumer2 = "test2"
    val consumer3 = "test3"
    val consumer4 = "test4"
    val s: Que = new Que(List(consumer1,consumer2,consumer3,consumer4))
    s.enqueue("2")
    s.enqueue("4")
    s.enqueue("5")
    assert(s.dequeue(consumer1)=="2")
    assert(s.dequeue(consumer1)=="4")
    assert(s.dequeue(consumer1)=="5")
    assert(s.dequeue(consumer1)==null)
    assert(s.dequeue(consumer2)=="2")
    assert(s.dequeue(consumer2)=="4")
    assert(s.dequeue(consumer2)=="5")
    assert(s.dequeue(consumer2)==null)
    assert(s.dequeue(consumer4)=="2")
    assert(s.dequeue(consumer4)=="4")
    assert(s.dequeue(consumer4)=="5")
    assert(s.dequeue(consumer4)==null)
    s.enqueue("6")
    s.enqueue("7")
    s.enqueue("8")
    assert(s.dequeue(consumer1)=="6")
    assert(s.dequeue(consumer1)=="7")
    assert(s.dequeue(consumer2)=="6")
    assert(s.dequeue(consumer4)=="6")
    assert(s.dequeue(consumer3)=="2")
    assert(s.dequeue(consumer3)=="4")
    intercept[ConsumerNotFoundException] {
      assert(s.dequeue("noconsumer")== null)
    }
  }

}
