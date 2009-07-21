package ipsim.io

import java.net.URL
import java.io.{ByteArrayOutputStream, IOException}

object IOUtility {
 def readWholeResource(resource: String): Option[String] = {
  val in = classOf[Class[_]].getResourceAsStream(resource)
  try {
   val out = new ByteArrayOutputStream
   try {
    var x = 0
    do {
     x = in.read
     if (x >= 0) out.write(x)
    } while (x >= 0)
    Some(new String(out.toByteArray, "UTF-8"))
   } finally out.close
  } catch { case e: IOException => None } finally in.close
 }
}

import java.io.{FileWriter, Closeable, File, BufferedWriter, Writer, IOException}

object PimpIO {
 def bracket[T, R](open: => T)(close: T => Unit)(f: T => R) = { val t = open
                                                                try { f(t) } finally { close(t) } }
 def bracketEithers[T, FailToAllocate, R, FailToExecute](open: => Either[FailToAllocate, T])(close: T => Unit)(f: T => Either[FailToExecute, R]) =
  open.right map (t => try { f(t) } finally { close(t) } )

 def fileWriter(file: File) = try { Right(new FileWriter(file)) } catch { case e: IOException => Left(e) }
 def bufferedWriter(writer: Writer) = try { Right(new BufferedWriter(writer))} catch { case e: IOException => Left(e) }
 def close[T <: Closeable](t: T) = try { t.close } catch { case e: IOException => e.printStackTrace }
 def using[T <: Closeable, U](t: => T)(f: T => U) = { val tt = t
                                                      try { f(tt) } finally { tt.close } } }
