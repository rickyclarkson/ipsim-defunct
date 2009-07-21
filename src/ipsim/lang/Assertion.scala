package ipsim.lang

object Assertion { def assertTrue(b: Boolean) {
 if (!b) throw new AssertionError 
} }
