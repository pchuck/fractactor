package net.ultrametrics.fractactor

import net.ultrametrics.math.Complex

/**
 * Mandelbrot set generator.
 *
 * Generate the mandelbrot set by composing a calculator that supports a
 * fractal function trait, a renderer, a peer and an image assembler.
 *
 * @param the name of the file to store the generated image
 * @param width the width in pixels of the image to generate
 * @param height the height in pixels of the image to generate
 * @param ll lower-left coordinate of the function bound in the complex plane
 * @param ur upper-right coordinate of the function bound in the complex plane
 * @param pixels whether to generate on a pixel or scanline basis
 */
class MandelbrotGenerator(fileName: String, 
                          width: Int, height: Int, iterations: Int, 
                          ll: Complex, ur: Complex, pixels: Boolean) 
{
  var renderer: Renderer = 
    if(fileName == Settings.SCREEN) new ScreenRenderer(width, height)
    else new FileRenderer(fileName, width, height)
  val calculator = new FractalCalculator(iterations) with MandelbrotFunction
  val peer = new FractalPeer(renderer, width, height)
  val assembler = new ImageAssembler(peer, calculator, width, height, ll, ur)

  def run = {
    calculator.start
    peer.start

    if(pixels) assembler.doByPixel else assembler.doByLine
  }
}

object MandelbrotGenerator {
  val usage = """
  Scala actor-based Mandelbrot set generator.
  To generate to the UI rather than file, specify "screen" for file option

  Usage: MandelbrotGenerator file iter width height rmin imin rmax imax [pixels]
    e.g. MandelbrotGenerator image.jpg 256 512 512 -1.5 -1.5 1.5 1.5 pixels
  """

  def main(args: Array[String]) {
    if(args.length < 8) {
      println(usage)
      System.exit(2)
    }
    else {
      val fileName = args(0)
      val iter = args(1).toInt
      val width = args(2).toInt
      val height = args(3).toInt
      val ll = new Complex(args(4).toDouble, args(5).toDouble)
      val ur = new Complex(args(6).toDouble, args(7).toDouble)
      val pixels = args.length > 8 && args(8) == Settings.PIXELS

      new MandelbrotGenerator(fileName, width, height, iter, ll, ur, pixels).run
    }
  }
}
