package ipsim.xml

import org.w3c.dom.{Node, NodeList, Document, Element}
import org.xml.sax.{SAXException, SAXParseException, ErrorHandler, InputSource}
import javax.xml.parsers.DocumentBuilderFactory
import java.io.StringReader

object DOMSimple {
 private implicit def nodeList2Seq(nodeList: NodeList): Seq[Node] = for (a <- 0 until nodeList.getLength) yield nodeList.item(a)
 def getChildNodes(root: Node, name: String) = root.getChildNodes filter (_.getNodeName == name)
 def getChildElementNode(root: Node, name: String) = root.getChildNodes.toStream flatMap (node => node match { case element: Element => Some(element)
                                                                                                               case _ => None } ) apply 0
 def getAttribute(node: Node, name: String) = Some(node.getAttributes.getNamedItem(name).getNodeValue) }

object XMLUtility { def xml2Dom(input: String): Option[Document] = {
 val factory=DocumentBuilderFactory.newInstance
 factory setValidating false
 val builder=factory.newDocumentBuilder
 builder setErrorHandler new ErrorHandler {
  def warning(e: SAXParseException) = ()
  def error(e: SAXParseException) = ()
  def fatalError(e: SAXParseException) = () }
 try { Some(builder parse new InputSource(new StringReader(input))) } catch { case e: SAXException => None } } }
