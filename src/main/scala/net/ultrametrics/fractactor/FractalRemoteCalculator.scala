package net.ultrametrics.fractactor

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors.remote._
import scala.actors.remote.RemoteActor._

import net.ultrametrics.math.Complex

/**
 * Remote fractal point calculator.
 */
class FractalRemoteCalculator(override val iterationLimit: Int, 
                              val name: String) 
  extends FractalCalculator(iterationLimit)
{
  override def act() {
    alive(9000)
    register('name, self)
    super.act()
  }
}
