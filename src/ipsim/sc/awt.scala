package ipsim.awt
import java.awt.{Dialog, Frame}
import java.util.Random

case class Point(x: Double, y: Double) { def +(other: Point) = Point(x+other.x, y+other.y)
                                         def -(other: Point) = Point(x-other.x, y-other.y)
                                         lazy val abs = Math.sqrt(x * x + y * y)
                                         def /(other: Double) = Point(x/other, y/other)
                                         def *(other: Double) = Point(x*other, y*other) }

object Point {
 def arbitraryPoint(implicit random: Random) = Point(random.nextInt(800)-400,random.nextInt(800)-400)
 val origin=Point(0,0)
 import ipsim.persistence.{ReadDelegate, WriteDelegate, XMLSerialiser, XMLDeserialiser}
 import org.w3c.dom.Node
 object delegate extends ReadDelegate[Point] with WriteDelegate[Point] {
  def writeXML(serialiser: XMLSerialiser, point: Point) =
   serialiser.writeAttribute("x", String.valueOf(point.x)).writeAttribute("y", String.valueOf(point.y))
  def readXML(deserialiser: XMLDeserialiser, node: Node, ignored: Option[Point]) = 
   Point(java.lang.Double.parseDouble(deserialiser.readAttribute(node, "x").get), java.lang.Double.parseDouble(deserialiser.readAttribute(node, "y").get))
  def construct = Some(Point(0,0))
  val identifier = "ipsim.persistence.delegates.PointDelegate" } }

import java.awt.{Container, Component}

object Implicits {
 implicit def container2Iterable(container: Container): Iterable[Component] =
  List(container) ++ container.getComponents.flatMap(_ match { case container: Container => container2Iterable(container) } )
 implicit def richDialog(dialog: Dialog) = new {
  def centreOnParent(frame: Frame) = dialog.setLocation(frame.getX + (frame.getWidth - dialog.getWidth) / 2,
                                                        frame.getY + (frame.getHeight - dialog.getHeight) / 2) } }

import javax.swing.{JDialog, JFrame}
object Components { def centreOnParent(dialog: JDialog, frame: JFrame) = dialog setLocation (frame.getX + (frame.getWidth - dialog.getWidth) / 2,
                                                                                             frame.getY + (frame.getHeight - dialog.getHeight) / 2) }
import java.awt.{Font, Graphics, Color}
import java.awt.font.FontRenderContext

object TextMetrics {
 val renderContext = new FontRenderContext(null, true, true)
 def size(font: Font, string: String) = { val rect = font.getStringBounds(string, 0, string.length, renderContext)
                                          Point(rect.getWidth, rect.getHeight) }

 def drawString(graphics: Graphics, string: String, position: Point, horizontalAlignment: HorizontalAlignment, verticalAlignment: VerticalAlignment,
                withYellowBackground: Boolean) = { val font = graphics.getFont
                                                   val size = TextMetrics.size(font, string)
                                                   val result = new Point(
                                                    if (horizontalAlignment == HorizontalAlignment.right) position.x - size.x else
                                                     if (horizontalAlignment == HorizontalAlignment.centre) position.x - size.x / 2 else position.x,
                                                    if (verticalAlignment == VerticalAlignment.bottom) position.y - size.y else
                                                     if (verticalAlignment == VerticalAlignment.centre) position.y - size.y / 2 else position.y)
                                                   if (withYellowBackground) {
                                                    val previous = graphics.getColor
                                                    graphics setColor Color.yellow
                                                    graphics fillRect (result.x.toInt - 2, result.y.toInt, size.x.toInt + 4, size.y.toInt)
                                                    graphics setColor previous }
                                                   graphics drawString (string, result.x.toInt, result.y.toInt + size.y.toInt * 3 / 4) } }
                                                                              
sealed abstract class HorizontalAlignment
object HorizontalAlignment { object right extends HorizontalAlignment
                             object centre extends HorizontalAlignment }

sealed abstract class VerticalAlignment
object VerticalAlignment { object top extends VerticalAlignment
                           object bottom extends VerticalAlignment
                           object centre extends VerticalAlignment }
