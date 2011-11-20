# fractactor makefile
#  
PKG=net.ultrametrics.fractactor
MAIN=MandelbrotClient
GEN=MandelbrotGenerator
UI=FractalRenderer
SCALA=scala-2.9.1
OUT=target/$(SCALA)/classes
IMG=/tmp/mandelbrot.jpg
P=256 512 512
OPTS=-Dactors.corePoolSize=20 -Dactors.maxPoolSize=40

all: compile

compile:
	sbt compile

usage:
	scala -cp $(OUT) $(PKG).$(MAIN)

# single pixel calculation test
simple: compile
	scala -cp $(OUT) $(PKG).$(MAIN) 256 0.5 0.5

# render to disk with actors per pixel or scanline
run: compile
#	scala -cp $(OUT) $(PKG).$(GEN) x.jpg $(P) -2.0 -1.5 1.0 1.5
	scala -cp $(OUT) $(PKG).$(GEN) $(IMG) $(P) -0.753 0.1164 -0.733 0.1364 lines

# render to screen with actor per pixel
pixels: compile
	scala $(OPTS) -cp $(OUT) $(PKG).$(GEN) screen $(P) -0.753 0.1164 -0.733 0.1364 pixels

# render to screen with actor per scanline
lines: compile
	scala $(OPTS) -cp $(OUT) $(PKG).$(GEN) screen $(P) -0.753 0.1164 -0.733 0.1364 lines

# view the rendered image
view: run
	xv /tmp/mandelbrot.jpg

package:
	sbt package

test_jar: package
	java -cp $(SCALA_HOME)/lib/scala-library.jar:target/$(SCALA)/fractal_2.9.1-0.1.jar net.ultrametrics.fractactor.MandelbrotGenerator

test:
	sbt test

clean:
	sbt clean

