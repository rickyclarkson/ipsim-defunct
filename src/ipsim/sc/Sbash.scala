package ipsim.sc

import java.io.File
import scala.io.Source

object Sbash {
 implicit def fileExtras(f: File): { def foreach(c: File => Unit): Unit
                                     def find: Seq[File] } = new {
  def foreach(c: File => Unit) = f.listFiles foreach c
  def find: Seq[File]=List(f) ++ f.listFiles ++ (f.listFiles filter (_.isDirectory) flatMap (_ find))
  def grep(file: File,p: String => Boolean) = for { line <- Source.fromFile(file).getLines
                                                    if p(line) } println(file.getName+": "+line.trim) }

 //why do I need getName in this?
 implicit def fileExtras(files: Seq[File]): { def grep(p: String => Boolean) } = new {
  def grep(p: String => Boolean) = for {
   file <- files 
   if file.isFile
   line <- Source.fromFile(file).getLines
   if p(line) } println(file+": "+line.trim) }

 implicit def string2file(s: String)=new File(s)
 implicit def classExtras(c: Class[_])=new { def printMethods = c.getMethods foreach println
                                             import java.net.URLClassLoader
                                             private def systemClassLoader = ClassLoader.getSystemClassLoader.asInstanceOf[URLClassLoader]

                                             def printSource = { val loader=systemClassLoader
                                                                 loader } }

 import ipsim.sc.jutil.Io._
 import System.getProperty

 def findClassName(name: String) = {
  val separator=getProperty("path.separator")
  val paths=getProperty("sun.boot.class.path")+separator+getProperty("java.class.path")+getProperty("env.classpath")
  paths split separator flatMap (getAllMatching(name,_)) }

 import java.util.zip.ZipFile
 import ipsim.sc.jutil.Iterables.enum2ArrayList
 def getAllMatching(name: String,path: String) = if (path.endsWith(".jar") && path.fileExists)
                                                  new ZipFile(path).entries map (_.toString) filter (_ endsWith ("/"+name+".class")) else List()

 def findSource(c: Class[_]) = {
  import java.io.{File,DataInputStream}
  val zipFile=new ZipFile( { val tmp2 = { val tmp=new File(System getProperty "java.home")
                                         if (tmp.getName endsWith "jre") tmp.getParentFile else tmp }
                            tmp2.listFiles filter (_.getName endsWith "src.zip") apply 0 } )
  val entry=zipFile getEntry (c.getName.replaceAll("\\.","/")+".java")
  val bArray=new Array[Byte](entry.getSize.toInt)
  val dis=new DataInputStream(zipFile getInputStream entry)
  try { dis readFully bArray
        new String(bArray) } finally { dis close } } }

// def findSource(m: Method) = { findSource(m.getDeclaringClass) 

class Metrics {
 def time(e: => Unit) = { val start = System.currentTimeMillis
                          e
                          System.currentTimeMillis - start }
 def compare(minMillis: Int)(one: => Unit)(two: => Unit) = {
  def f(it: => Unit)(times: Int): Unit = { it
                                           if (times>0) f(it)(times-1) }
  def g(times: Int): (Long, Long) = { val t1 = time(f(one)(times))
                                      val t2 = time(f(two)(times))
                                      if (Math.max(t1, t2)>minMillis) (t1, t2) else g(times*10) }
  g(1) } }
