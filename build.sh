#!/bin/bash

mkdir build
CLASSPATH='build:lib/scalax-0.0.jar:lib/anylayout.jar:lib/fj.jar'
export JAVA_OPTS=-Xss1M
find src/ipsim/sc -name \*.scala | xargs scalac -d build -classpath $CLASSPATH -deprecation &&
find src/ipsim -name \*.java | xargs javac -d build -deprecation -classpath $CLASSPATH -Xlint:unchecked &&
find src/ipsim/sc2 -name \*.scala | xargs scalac -d build -classpath $CLASSPATH -deprecation 