package net.ultrametrics.fractactor

import scala.actors.Actor
import scala.actors.Actor._

import net.ultrametrics.math.Complex

/**
 * A client to exercise MandelbrotCalculator.
 *
 * Accepts on the command-line a set of parameters describing a
 * single point to be calculated. Asynchronously sends a message to
 * the calculator actor and receives a response.
 */
class MandelbrotClient extends Actor {
  def act() {
    loop {
      react {
        // receive the calculation result message from the calculator actor
        case SimpleResponse(c, result) => { 
          println("client received result: " + c + ": " + result)
          System.exit(0) // terminate after receiving the result
        }
        case msg => println("unknown message: " + msg)
      }
    }
  }

  def orchestrate(i: Int, c: Complex) {
    val calculator = new FractalCalculator(i) 
      with MandelbrotFunction with ScatterPointScaler
    calculator.start // start the calculator
    calculator !! SimpleRequest(c, this) // send calculation request message
  }
}

object MandelbrotClient {
  val usage = """
    Usage: MandelbrotClient iterationLimit real imaginary
      e.g. MandelbrotClient 256 1.05 -1.62
  """

  def main(args: Array[String]) {
    if(args.length < 3) {
      println(usage)
      System.exit(2)
    }
    else {
      val iterationLimit = args(0).toInt
      val c = new Complex(args(1).toDouble, args(2).toDouble)
      val client = new MandelbrotClient
      client.start
      client.orchestrate(iterationLimit, c)
    }
  }
}
