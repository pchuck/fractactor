package net.ultrametrics.fractactor

import scala.actors.Actor
import scala.actors.Actor._
import scala.actors.remote._
import scala.actors.remote.RemoteActor._

import net.ultrametrics.math.Complex
import net.ultrametrics.fractactor.Settings._
import net.ultrametrics.fractactor.Functions._

/**
 * Remote fractal point calculator.
 */
class RemoteFractalCalculator(val hostName: String, 
                              val iterationLimit: Int)
  extends HolomorphicFunction with Actor
{
  def act() {
    RemoteActor.classLoader = getClass().getClassLoader()
    alive(CALC_PORT)
    register('lambda, self)
    val remoteClient = select(Node(CLIENT_HOST, CLIENT_PORT), 'client)

    loop {
      react {
        case VerySimpleRequest(c) => {
          println("calculator received simple request: " + c);
          remoteClient ! SimpleResponse(c, calculate(c)) 
        }
        case msg => println("unknown message: " + msg)
      }
    }
  }
}

object RemoteFractalCalculator {
  val usage = """
  Remote fractal calculator 

  Usage: RemoteFractalCalculator hostName function iterations

  e.g. To start a remote mandelbrot calculator, on the host 'lambda'
    RemoteFractalCalculator lambda mandelbrot iterations
  """

  def main(args: Array[String]) {
    if(args.length < 3) {
      println(usage)
      System.exit(2)
    }
    else {
      val hostName = args(0)
      val functionName = args(1)
      val iterations = args(2).toInt

      println("starting remote actor on '" + hostName + "'..")
      var calculator = functionName match {
        case MANDELBROT => 
          new RemoteFractalCalculator(hostName, iterations) 
            with MandelbrotFunction
      }
      calculator.start
    }
  }
}
