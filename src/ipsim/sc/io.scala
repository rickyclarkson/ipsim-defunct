package ipsim.io

import java.net.URL
import scalax.io.InputStreamResource
import java.io.IOException

object IOUtility {
 def readWholeResource(resource: String): Option[String] = try { Some(new String(InputStreamResource.url(resource).slurp,"UTF-8")) } catch {
  case e: IOException => None } }
                                                                  
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
