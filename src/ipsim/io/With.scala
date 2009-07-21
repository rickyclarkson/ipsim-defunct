package ipsim.io

import java.io.Closeable

object With { def using[R](c: Closeable)(f: => R)=try { f } finally { c.close }
              def usingF[C <: Closeable,R](c: C)(f: C => R)=try { f(c) } finally { c.close } }