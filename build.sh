#!/bin/bash

mkdir build
CLASSPATH='build;lib/scalax-0.0.jar;lib/anylayout.jar;lib/fj.jar'
export JAVA_OPTS=-Xss1M
find src/ipsim -name \*.scala | xargs scalac -d build -classpath $CLASSPATH -deprecation &&
scala -classpath $CLASSPATH ipsim.tests.RunTests
