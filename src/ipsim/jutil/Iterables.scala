package ipsim.sc.jutil

/*object Iterables
{
 implicit def iterable2foreach[T](iterable: java.lang.Iterable[T]) = new {
  def foreach(closure: T => Unit) = {
   val iterator=iterable iterator
   while (iterator hasNext) closure(iterator next) } }

 implicit def enum2ArrayList[T](e: java.util.Enumeration[T]) = { val result=new scala.collection.jcl.ArrayList[T]
                                                                 while (e hasMoreElements) result add (e nextElement)
                                                                 result }
}*/
