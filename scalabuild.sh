#!/bin/bash
~/scala-2.7.1.final/bin/scalac -target:jvm-1.5 -deprecation -unchecked $(find src/ipsim/sc -name \*.scala) -classpath build -d build
