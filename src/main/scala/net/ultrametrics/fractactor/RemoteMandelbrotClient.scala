package net.ultrametrics.fractactor

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors.remote._
import scala.actors.remote.RemoteActor._

import net.ultrametrics.fractactor.Settings._
import net.ultrametrics.math.Complex

/**
 * A remote client to exercise MandelbrotCalculator.
 *
 * Accepts on the command-line a set of parameters describing a
 * single point to be calculated. Asynchronously sends a message to
 * the remote calculator actor and receives a response.
 */
class RemoteMandelbrotClient extends Actor with Serializable {
  RemoteActor.classLoader = getClass().getClassLoader()
  val calculator = select(Node("localhost", CALC_PORT), 'lambda)

  def act() {
    alive(CLIENT_PORT)
    register('client, self)
    loop {
      react {
        // receive the calculation result message from the calculator actor
        case VerySimpleRequest(c) => {
          calculator !! VerySimpleRequest(c) 
        }
        case SimpleResponse(c, result) => { 
          println("client received result: " + c + ": " + result)
          System.exit(0) // terminate after receiving the result
        }
        case msg => println("unknown message: " + msg)
      }
    }
  }

  def orchestrate(i: Int, c: Complex) {
      this !! VerySimpleRequest(c) // send calculation request message
  }
}

object RemoteMandelbrotClient {
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
      val client = new RemoteMandelbrotClient
      client.start
      client.orchestrate(iterationLimit, c)
    }
  }
}
