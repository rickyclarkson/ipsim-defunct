package ipsim.persistence

import ipsim.network.Network
import org.w3c.dom.{Document, Node}
import ipsim.xml.{DOMSimple, XMLUtility}
import scala.collection.mutable
import ipsim.lang.Implicits._

object XMLDeserialiser { def fromString(xml: String) = XMLUtility.xml2Dom(xml) map (doc => XMLDeserialiser(doc)) }

case class XMLDeserialiser(private[persistence] input: Document) {
 import DOMSimple.getAttribute
 val objectsRead = new mutable.HashMap[Integer, Any]

 def readObject[T](delegate: ReadDelegate[T]): Option[T] = readObject(DOMSimple.getChildElementNode(input, "object"), delegate)
 def readObject[T](node: Node, name: String, delegate: ReadDelegate[T]): Option[T] = {
  (for (node2 <- DOMSimple.getChildNodes(node, "object").toStream; if getAttribute(node2, "name")==name) yield readObject(node2, delegate))(0)
 }
 def readObject[T](node: Node, delegate: ReadDelegate[T]): Option[T] = {
  val id = Integer.parseInt(getAttribute(node, "id").get)
  objectsRead.get(id) match { case Some(result) => Some(result.asInstanceOf[T])
                              case None => { val obj = delegate.construct
                                             obj foreach (objectsRead.put(id, _))
                                             val obj2 = delegate.readXML(this, node, obj)
                                             objectsRead.put(id, obj2)
                                             Some(obj2) } } }
 def readAttribute(node: Node, name: String): Option[String] = DOMSimple.getChildNodes(node, "attribute").toStream flatMap (element =>
  if (getAttribute(element, "name") == Some(name)) getAttribute(element, "value") else None) firstOption
 def getObjectNames(node: Node): Iterable[String] = DOMSimple.getChildNodes(node, "object") map (readAttribute(_, "name").get)
 def typeOfChild(node: Node, name: String) = DOMSimple.getChildNodes(node, "object").toStream filter(getAttribute(_, "name") == name) head }

trait ReadDelegate[T] { def construct: Option[T]
                        def readXML(deserialiser: XMLDeserialiser, node: Node, obj: Option[T]): T }

trait WriteDelegate[T] { def writeXML(serialiser: XMLSerialiser, obj: T): XMLSerialiser
                         def identifier: String }

import java.io.Writer
case class XMLSerialiser(writer: Writer) {
 private var alreadyStoredVar: List[AnyRef] = Nil

 def writeObject[T <: AnyRef](obj: T, name: String, delegate: WriteDelegate[T]) = {
  val id = { var tmp = alreadyStoredVar indexOf obj
             if (tmp < 0)
              tmp = alreadyStoredVar.size
             tmp }
  writer.write("<object name=\"" + name + "\" serialiser=\"" + delegate.identifier + "\" id=\"" + id + "\">")
  if (id == alreadyStoredVar.size) { alreadyStoredVar = alreadyStoredVar ++ List(obj)
                                  delegate.writeXML(this, obj) }
  writer.write("</object>")
  this }

 def writeAttribute(name: String, value: String) =
  writer.write("<attribute name=\"" + xmlEncode(name) + "\" value=\"" + xmlEncode(value) + "\"/>") then this
 def xmlEncode(value: String) = value replaceAll ("\"", "&quot;")
 def close = writer.close }

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
 implicit val network = new Network
 val context = network.contexts append new NetworkContext
 
 val computer = Computer(Point(20, 20))
 context.visibleComponentsVar ::= computer

 val card = Card(false, Right(computer))
 context.visibleComponentsVar ::= card
                                                              
 computer.ipForwardingEnabled = true
 val writer = new StringWriter
 val serialiser = new XMLSerialiser(writer)
 serialiser.writeObject(network, "network", network.delegate)
                                                                
 val deserialiser = XMLDeserialiser.fromString(writer.getBuffer.toString).get
 deserialiser.readObject(network.delegate).get
 network.all exists (_ fold (card => false, _.ipForwardingEnabled, cable => false, hub => false)) } }
