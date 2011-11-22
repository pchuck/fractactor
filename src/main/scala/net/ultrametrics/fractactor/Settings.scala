package net.ultrametrics.fractactor

object Settings {
  // color scaling
  val Scale = 1<<16 : Int

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
