package ipsim.sc.jutil

import java.io.File

object Io
{
 implicit def stringWithFileExists(s: String)=new { def fileExists=new File(s) exists }
}