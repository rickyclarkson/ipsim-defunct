package ipsim.lang
import scala.Stream
import scala.collection.mutable

case class StreamProperty[T](var stream: Stream[T]) {
 
 def append(t: T) = { stream = stream append List(t)
                      t }

 def prependIfNotPresent[U <: T](u: U) = { if (!stream.exists(_==u)) stream = Stream.cons(u, stream)
                                           u }
 def prepend[U <: T](u: U) = { stream = Stream.cons(u, stream)
                               u }

 def clear = stream = Stream.empty
 def filter(p: T => Boolean) = stream = stream filter p
 def foreach(e: T => Unit) = stream foreach e
 def isEmpty = stream isEmpty
 def size = stream size }

case class RichStream[T](val stream: Stream[T]) {
 def merge(other: Stream[T]) = stream ++ other filter Unique.unique }

object Unique {
 def unique[T] = { val seen = new mutable.HashSet[T]
                   (t: T) => if (seen contains t) false else { seen += t
                                                               true } } }
object Implicits {
 implicit def richList[T](list: List[T]) = RichList(list)
 case class RichList[T](list: List[T]) { def set(index: Int, t: T) = list.zipWithIndex map (x => if (x._2==index) t else x._1)
                                         def zipWithTail = list.zip(list.tail)
                                         def hasDuplicates = list.removeDuplicates.size == list.size
                                         def prependIfNotPresent(t: T) = if (list exists (_ == t)) list else t :: list
                                         def merge(other: List[T]) = list ++ other filter Unique.unique }

 implicit def streamWithMerge[T](stream: Stream[T]) = RichStream(stream)
 implicit def richAny[T](t: T) = RichAny(t)
 case class RichAny[T](t: T) { def then[T](f: => T) = f
                               def useAndReturn(ef: T => Unit): T = ef(t) then t }
 implicit def richIterable[T](it: Iterable[T]) = RichIterable(it)
 case class RichIterable[T](it: Iterable[T]) { def head: T = it.toStream.apply(0)
                                               def headOption = if (it.isEmpty) None else Some(head)
                                               def hasDuplicates = richList(it.toList).hasDuplicates
                                               def toOption = headOption
                                               def size = it.toList.size
                                               def apply(index: Int) = it.toList apply index }

 implicit def richOption[T](op: Option[T]) = RichOption(op)
 case class RichOption[T](op: Option[T]) { def fold[U](ifNone: => U, ifSome: T => U) = op match { case Some(t) => ifSome(t)
                                                                                                  case _ => ifNone } }
 implicit def boolTernary(b: Boolean) = new { def ?[T](then: => T) = new { def !(or: => T) = if (b) then else or } }
 implicit def tupleWithDiffers[T, U](t: (T, T)) = new { def differs[U](f: T => U) = f(t._1) != f(t._2) }
 implicit def bool2Bool(b: java.lang.Boolean) = b.booleanValue }

object Runnables { def throwRuntimeException = new Runnable { def run = throw new RuntimeException }
                   def nothing = new Runnable { def run = () } }
