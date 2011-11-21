package net.ultrametrics.fractactor

import net.ultrametrics.math.Complex
import net.ultrametrics.fractactor.Functions._
import net.ultrametrics.fractactor.Renderers._

/**
 * Fractal engine.
 *
 * Generate a fractal set by composing a function calculator, a renderer,
 * a peer and an image assembler.
 *
 * @param functionName name of the fractal function to generate
 * @param rendererName name of the renderer to store or display the image
 * @param mode name of mode (scanline or pixels) to use in image assembly
 * @param fileName the name of the file to store the generated image
 * @param width the width in pixels of the image to generate
 * @param height the height in pixels of the image to generate
 * @param ll lower-left coordinate of the function bound in the complex plane
 * @param ur upper-right coordinate of the function bound in the complex plane
 */
class FractalGenerator(functionName: String, rendererName: String, 
                       mode: String, fileName: String,
                       width: Int, height: Int, iterations: Int, 
                       ll: Complex, ur: Complex) 
{
  var renderer: Renderer = rendererName match {
    case SCREEN => new ScreenRenderer(width, height)
    case FILE => new FileRenderer(fileName, width, height)
  }
  var calculator = functionName match {
    case MANDELBROT => new FractalCalculator(iterations) with MandelbrotFunction
  }
  val peer = new FractalPeer(renderer, width, height)
  val assembler = new ImageAssembler(peer, calculator, width, height, ll, ur)

  def run = {
    calculator.start
    peer.start

    mode match {
      case Mode.PIXELS => assembler.doByPixel
      case Mode.SCANLINES => assembler.doByScanline
    }
  }
}

object FractalGenerator {
  val usage = """
  Scala actor-based fractal engine.

  Usage: FractalGenerator type render mode iter w h rmin imin rmax imax [file]

  e.g. To generate the mandelbrot set, using 1 actor per pixel, to a file:
    FractalGenerator mandelbrot file pixels 256 512 512 -1.5 -1.5 1.5 1.5 x.jpg

  e.g. To generate the mandelbrot set, w/ 1 actor per scanline, to the screen:
    FractalGenerator mandelbrot screen pixels 256 512 512 -1.5 -1.5 1.5 1.5
  """

  def main(args: Array[String]) {
    if(args.length < 10) {
      println(usage)
      System.exit(2)
    }
    else {
      val functionName = args(0)
      val rendererName = args(1)
      val mode = args(2)
      val iter = args(3).toInt
      val width = args(4).toInt
      val height = args(5).toInt
      val ll = new Complex(args(6).toDouble, args(7).toDouble)
      val ur = new Complex(args(8).toDouble, args(9).toDouble)
      val fileName = if (args.length > 10) args(10) else null

      new FractalGenerator(functionName, rendererName, mode, fileName, 
                           width, height, iter, ll, ur)
      .run
    }
  }
}
