# fractactor makefile
# 
# this project can be built, run, deployed, etc, using sbt, without make.
# however, the following make targets are convenient hooks and save some typing.
#
PKG=net.ultrametrics.fractactor
MAIN=FractalGenerator
TEST=MandelbrotClient
RMT=RemoteFractalCalculator
SCALA=scala-2.9.1
IMG=/tmp/mandelbrot.jpg
POI=256 512 512 -0.753 0.1164 -0.733 0.1364
OPTS=-Dactors.corePoolSize=20 -Dactors.maxPoolSize=40

all: compile

compile:
	sbt compile

# single pixel calculation test
simple: compile
	sbt "run-main $(PKG).$(TEST) 256 0.5 0.5"

# render to disk with actors per pixel or scanline
run: compile
	sbt "run-main $(PKG).$(MAIN) mandelbrot file scanlines $(POI) $(IMG)"

# render to screen with actor per pixel
pixels: compile
	sbt "run-main $(PKG).$(MAIN) mandelbrot screen pixels $(POI)"

# render to screen with actor per scanline
lines: compile
	sbt "run-main $(PKG).$(MAIN) mandelbrot screen scanlines $(POI)"

# view the rendered image
view: 
	xv /tmp/mandelbrot.jpg

remote_client: compile
	sbt "run-main $(PKG).RemoteMandelbrotClient 256 0.5 0.5"

remote_actor:
	sbt "run-main $(PKG).$(RMT) localhost mandelbrot 256"

usage:
	sbt "run-main $(PKG).$(MAIN)"

package:
	sbt package

test_jar: package
	java -cp $(SCALA_HOME)/lib/scala-library.jar:target/$(SCALA)/fractal_2.9.1-0.1.jar net.ultrametrics.fractactor.MandelbrotGenerator

test:
	sbt test

clean:
	sbt clean

