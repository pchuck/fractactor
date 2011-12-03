package net.ultrametrics.fractactor

import scala.swing._
import java.awt.image.BufferedImage

class RenderPanel() extends Panel 
{
  var image = null : BufferedImage

  def create(width: Int, height: Int) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    // set initial background white to highlight async progress of actors
    for(x <- 0 until width)
      for(y <- 0 until height)
        image.setRGB(x, y, 0xffffff);
  }

  override def paintComponent(g: Graphics2D) = g.drawImage(image, 0, 0, null)

  def drawScanline(width: Int, y: Int, scanline: Array[Int]) =
    image.setRGB(0, y, width, 1, scanline, 0, 1)

  def drawPixel(x: Int, y: Int, value: Int) =
    image.setRGB(x, y, value)
}
