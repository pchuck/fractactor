package net.ultrametrics.fractactor

import scala.actors.Actor
import scala.actors.Actor._

import net.ultrametrics.math.Complex


/**
 * Fractal peer.
 *
 * Asynchronous and actor-based
 *
 * This class receives messages from a fractal calculating actor.
 * Persistence/drawing of the calculated pixels is delegated to a
 * provided class exhibiting the rendering trait.
 *
 * @param renderer a class capable of rendering the generated fractal image
 * @param calculator algorithm provider and calculation actor
 * @param width the width in pixels of the image to generate
 * @param height the height in pixels of the image to generate
 * @param ll lower-left coordinate of the function bound in the complex plane
 * @param ur upper-right coordinate of the function bound in the complex plane
 */
class FractalPeer(val renderer: Renderer, 
                  val width: Int, val height: Int) extends Actor with Serializable
{
  var pendingPixels = height * width // # of pixels == amount of pending work
  var pendingLines = height
  var startMillis = 0 : Long

  def shutdown() {
    try {
      renderer.save()
    }
    println("elapsed time: " + (System.currentTimeMillis - startMillis) + "ms")
    if(renderer.isInstanceOf[FileRenderer]) // !!! hack
      System.exit(0)
  }

  def act() {
    startMillis = System.currentTimeMillis
    renderer.create(width, height)

    loop {
      react {
        case LineResponse(y, scanline) => {
//            print("|")
/*
          for(x <- 0 until width)
            renderer.set(x, y, scanline(x))
*/
          renderer.set(y, scanline)
          pendingLines -= 1

          if(pendingLines == 0) {
            println("all actors complete. writing image..")
            shutdown
          }
        }
        case PixelResponse(x, y, r) => {
//            print("-")
          renderer.set(x, y, r)
          pendingPixels -= 1

          if(pendingPixels == 0) {
            println("all actors complete. writing image..")
            shutdown
          }
        }
        case msg => println("unknown message: " + msg)
      }
    }
  }
}
