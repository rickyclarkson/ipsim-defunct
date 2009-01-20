package ipsim.sc.jutil

object SwingUtilities
{
 def invokeLater(f: => Unit) = javax.swing.SwingUtilities.invokeLater(new Runnable { def run = f } )
}