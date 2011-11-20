#!/bin/sh
#
# fractactor
#
java -cp $SCALA_HOME/lib/scala-library.jar:fractactor_2.9.1-0.1.jar net.ultrametrics.fractactor.MandelbrotGenerator $*
