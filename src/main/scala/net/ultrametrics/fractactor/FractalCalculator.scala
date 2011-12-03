package net.ultrametrics.fractactor

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors.remote._
import scala.actors.remote.RemoteActor._

import net.ultrametrics.math.Complex

// calculation request/response messages
case class VerySimpleRequest(c: Complex)
case class SimpleRequest(c: Complex, client: Actor)
case class SimpleResponse(c: Complex, result: Int)
case class PixelRequest(x: Int, y: Int, c: Complex, client: Actor)
case class PixelResponse(x: Int, y: Int, r: Int)
case class LineRequest(y: Int, count: Int, re1: Double, re2: Double, 
                       im: Double, client: Actor)
case class LineResponse(y: Int, scanline: Array[Int])

/**
 * Fractal point calculator.
 * 
 * Given a coordinate in the complex plane, computes the corresponding
 * value of a holomorphic function (such as the Mandelbrot set) layered
 * onto this calculator as a trait. PointScaler trait is used to
 * transform the calculated fractal point into a color in the viewplane.
 *
 * Functions as an actor which responds to Complex coordinate messages
 * or a standalone function which synchronously computes a point.
 */
class FractalCalculator(val iterationLimit: Int) 
  extends HolomorphicFunction with Actor
{
  def act() {
    loop {
      react {
        case LineRequest(y, count, re1, re2, im, client) => {
//          print("_")
          val results = new Array[Int](count)
          val increment = (re2 - re1) / count
          var re = re1
          for(x <- 0 until count) {
            results(x) = calculate(new Complex(re, im))
            re += increment
          }
          client !! LineResponse(y, results) 
        }
        case PixelRequest(x, y, c, client) => {
//          print("+")
          client !! PixelResponse(x, y, calculate(c)) 
        }
        case SimpleRequest(c, client) => {
          client !! SimpleResponse(c, calculate(c)) 
        }
        case msg => println("unknown message: " + msg)
      }
    }
  }
}


