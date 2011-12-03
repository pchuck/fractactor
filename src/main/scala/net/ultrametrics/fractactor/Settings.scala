package net.ultrametrics.fractactor

import scala.actors.Actor

import net.ultrametrics.math.Complex

// calculation request/response message types
case class VerySimpleRequest(c: Complex)
case class SimpleRequest(c: Complex, client: Actor)
case class SimpleResponse(c: Complex, result: Int)
case class PixelRequest(x: Int, y: Int, c: Complex, client: Actor)
case class PixelResponse(x: Int, y: Int, r: Int)
case class LineRequest(y: Int, count: Int, re1: Double, re2: Double, 
                       im: Double, client: Actor)
case class LineResponse(y: Int, scanline: Array[Int])

/**
 * System-wide settings and constants.
 */
object Settings {
  // color scaling
  val Scale8to24Bit = 1<<16 : Int

  // bailout threshold
  val Threshold = 4.0

  val CLIENT_PORT = 9999
  val CLIENT_HOST = "localhost"
  val CALC_PORT = 10000
}

object Mode {
  val PIXELS = "pixels"
  val SCANLINES = "scanlines"
}

object Functions {
  val MANDELBROT = "mandelbrot"
  val NOOP = "noop"
}

object Renderers {
  val SCREEN = "screen"
  val FILE = "file"
}
