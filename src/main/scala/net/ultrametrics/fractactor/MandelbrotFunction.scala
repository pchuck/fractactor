package net.ultrametrics.fractactor

import net.ultrametrics.math.Complex

/**
 * A function for calculating the mandelbrot set.
 */
trait MandelbrotFunction extends HolomorphicFunction 
{
  val iterationLimit: Int
//  val func: PointScaler
  val ps: PointScaler

  // iterative
  override def calculate(c: Complex): Int = {
    var iteration = 0
    var z = new Complex(0, 0)

    do {
      z = z * z + c
      iteration += 1
    } while(iteration < iterationLimit && z.scalar < Settings.Threshold)

    ps.iToRGB(iteration)
  }

  // recursive
  def calculate(iteration: Int, z: Complex, c: Complex): Int = {
    if(iteration < iterationLimit && z.scalar < Settings.Threshold)
      calculate(iteration+1, z*z+c, c)
    else
      ps.iToRGB(iteration)
  }
}
