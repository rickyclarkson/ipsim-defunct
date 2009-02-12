package ipsim.persistence

import scala.collection.immutable.Map
import scala.xml.{Elem, Node, XML}

case class RichNode(node: Node) {
 def /@(name: String) = (node \ "attribute" filter (_ \ "@name" == name)) \ "@value" text match { case "" => throw null
                                                                                                              case s => s }
}

object Implicits {
 implicit def node2RichNode(node: Node) = RichNode(node)
}

trait Serial[T] { def toXML(t: T): XMLSerialiser => (XMLSerialiser, Elem)
                  def fromXML(elem: Elem): XMLDeserialiser => (XMLDeserialiser, T) }

case class XMLSerialiser(alreadySeen: List[(Int, Any)]) {
 def toXML[T](t: T, name: String)(implicit delegate: Serial[T]): (XMLSerialiser, Elem) = alreadySeen.find(_._2 == t) match {
  case Some((id, _)) => this -> <object name={name} id={id.toString}> </object>
  case _ => delegate.toXML(t)(this) match {
   case (ser, <object>{rest @ _*}</object>) => val (newSer, id) = ser.newId(t)
                                               newSer -> <object name={name} id={id.toString}> {rest} </object>
  }
 }

 def newId(obj: Any): (XMLSerialiser, Int) = {
  for { x <- 1 to Integer.MAX_VALUE
        if !alreadySeen.exists(_._1 == x) } return XMLSerialiser((x -> obj) :: alreadySeen) -> x
  throw null
 }
}

case class XMLDeserialiser(alreadySeen: List[(Int, Any)]) {
 def fromXML[T](elem: Elem)(implicit serial: Serial[T]): (XMLDeserialiser, T) = {
  val id = (elem \ "@id" toString).toInt
  alreadySeen.find(_._1 == id) match {
   case Some((_, obj)) => this -> obj.asInstanceOf[T]
   case None => val (newDeserialiser, t) = serial.fromXML(elem)(this)
                XMLDeserialiser(id -> t :: newDeserialiser.alreadySeen) -> t
  }
 }
}

object XMLSerialiser { def apply(): XMLSerialiser = XMLSerialiser(Nil) }                                                  
object XMLDeserialiser { def apply(): XMLDeserialiser = XMLDeserialiser(Nil)                       
                         def fromString(xml: String): Elem = XML.loadString(xml) }

import ipsim.network.Network
import Network._

object LogRetentionTest { def apply = { implicit var network = new Network
                                        network.log ++= List("Sample Data")
                                        val xml = network.saveToString
                                        network = new Network
                                        network.loadFromString(xml)
                                        "Sample Data" == network.log(0) } }

import ipsim.awt.Point
import ipsim.network.{NetworkContext, Computer, Card}
import java.io.StringWriter

object XMLSerialisationTest { def testComputerIPForwarding = () => {
 val writer = new StringWriter

 {
  implicit val network = new Network
  val context = network.contexts append new NetworkContext
 
  val computer = Computer(Point(20, 20))
  context.visibleComponentsVar ::= computer

  val card = Card(false, Right(computer))
  context.visibleComponentsVar ::= card
                                                              
  computer.ipForwardingEnabled = true
  
  val serialiser = XMLSerialiser()
  XML.write(writer, serialiser.toXML(network, "network"), "UTF-8", true, null)
 }
                                                   
 val network = XMLDeserialiser().fromXML[Network](XMLDeserialiser.fromString(writer.getBuffer.toString))
 network.all exists (_ fold (card => false, _.ipForwardingEnabled, cable => false, hub => false)) } }
