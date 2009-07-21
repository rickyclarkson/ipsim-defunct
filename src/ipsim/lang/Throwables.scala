package ipsim.lang

import java.io.{PrintWriter,StringWriter}
import ipsim.io.With.usingF

object Throwables { def asString(throwable: Throwable)={ val answer=new StringWriter
                                                         usingF(new PrintWriter(answer))(throwable.printStackTrace(_))
                                                         answer.toString } }