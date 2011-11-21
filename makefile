# fractactor makefile
#  
PKG=net.ultrametrics.fractactor
MAIN=MandelbrotClient
GEN=FractalGenerator
UI=FractalRenderer
SCALA=scala-2.9.1
OUT=target/$(SCALA)/classes
IMG=/tmp/mandelbrot.jpg
P=256 512 512
OPTS=-Dactors.corePoolSize=20 -Dactors.maxPoolSize=40

all: compile

compile:
	sbt compile

# single pixel calculation test
simple: compile
	scala -cp $(OUT) $(PKG).$(MAIN) 256 0.5 0.5

# render to disk with actors per pixel or scanline
run: compile
#	scala -cp $(OUT) $(PKG).$(GEN) x.jpg $(P) -2.0 -1.5 1.0 1.5
	scala -cp $(OUT) $(PKG).$(GEN) mandelbrot file scanlines $(P) -0.753 0.1164 -0.733 0.1364 $(IMG)

# render to screen with actor per pixel
pixels: compile
	scala $(OPTS) -cp $(OUT) $(PKG).$(GEN) mandelbrot screen pixels $(P) -0.753 0.1164 -0.733 0.1364

# render to screen with actor per scanline
lines: compile
	scala $(OPTS) -cp $(OUT) $(PKG).$(GEN) mandelbrot screen scanlines $(P) -0.753 0.1164 -0.733 0.1364

# view the rendered image
view: run
	xv /tmp/mandelbrot.jpg

usage:
	scala $(OPTS) -cp $(OUT) $(PKG).$(GEN)

package:
	sbt package

test_jar: package
	java -cp $(SCALA_HOME)/lib/scala-library.jar:target/$(SCALA)/fractal_2.9.1-0.1.jar net.ultrametrics.fractactor.MandelbrotGenerator

test:
	sbt test

clean:
	sbt clean

