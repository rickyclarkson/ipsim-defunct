#!/bin/bash
~/scala-2.7.1.final/bin/scalac $(find src/ipsim/sc2 -name \*.scala) -target:jvm-1.5 -deprecation -classpath build -d build