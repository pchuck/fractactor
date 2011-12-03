package net.ultrametrics.fractactor

import net.ultrametrics.math.Complex

/**
 * A function for calculating the mandelbrot set.
 *
 * By default, uses PointScaler which is a noop on the calculated value.
 * Functions utilizing this trait, however, can mixin an alternate 
 * PointScaler implementation to achieve different point-value mappings.
 */
trait MandelbrotFunction extends HolomorphicFunction with PointScaler
{
  val iterationLimit: Int

  // iterative
  override def calculate(c: Complex): Int = {
    var iteration = 0
    var z = new Complex(0, 0)

    do {
      z = z * z + c
      iteration += 1
    } while(iteration < iterationLimit && z.scalar < Settings.Threshold)

    transform(iteration)
  }

  // recursive
  def calculate(iteration: Int, z: Complex, c: Complex): Int = {
    if(iteration < iterationLimit && z.scalar < Settings.Threshold)
      calculate(iteration+1, z*z+c, c)
    else
      transform(iteration)
  }
}
