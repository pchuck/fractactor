package net.ultrametrics.fractactor

import net.ultrametrics.math.Complex

/**
 * A trait for any holomorphic function that can be calculated from a
 * point in the complex plane.
 */
trait HolomorphicFunction
{
  /**
   * Calculate the function at a single point.
   * @param c a point in the complex plane
   * @return an evaluation of the function
   */
  def calculate(c: Complex): Int = c.scalar.toInt
}
