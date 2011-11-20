package net.ultrametrics.fractactor

import scala.actors.Actor

import net.ultrametrics.math.Complex

/**
 * Image assembler.
 *
 * Traverses an image in the cartesian space, sending messages
 * to the provided actor to perform calculations for corresponding points
 * in the complex plane.
 */
class ImageAssembler(val peer: Actor, 
                     val calculator: FractalCalculator, 
                     val width: Int, val height: Int, 
                     val ll: Complex, val ur: Complex)
{
  val size = ur - ll // size of the region in the imaginary plane

  def doByPixel() {
    var x, y = 0
    var xx, yy = 0 : Double
    for(y <- 0 until height) {
      yy = y * size.im / height + ll.im
      for(x <- 0 until width) {
        xx = x * size.re / width + ll.re
        calculator !! PixelRequest(x, y, new Complex(xx, yy), peer)
      }
    }
  }

  def doByLine() {
    var y = 0
    var yy = 0 : Double
    for(y <- 0 until height) {
      yy = y * size.im / height + ll.im
      calculator !! LineRequest(y, width, ll.re, ur.re, yy, peer)
    }
  }

  def doByLine(y: Int) {
    if(y < height) {
      val yy = y * size.im / height + ll.im
      calculator !! LineRequest(y, width, ll.re, ur.re, yy, peer)
      doByLine(y+1);
    }
  }
}
