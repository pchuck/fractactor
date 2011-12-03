package net.ultrametrics.fractactor

import java.awt.image.BufferedImage
import java.io.IOException
import java.io.File
import javax.imageio.ImageIO

/**
 * File-based image renderer, backed by a BufferedImage.
 */
class FileRenderer(val fileName: String, val width: Int, val height: Int) 
extends Renderer
{
  var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

  def create(width: Int, height: Int) = { }

  def set(x: Int, y: Int, value: Int) = image.setRGB(x, y, value)

  def set(y: Int, scanline: Array[Int]) = {
    for(x <- 0 until width)
      image.setRGB(x, y, scanline(x))
  }

  def save() = {
    try
      ImageIO.write(image, "jpg", new File(fileName))
    catch {
      case e:IOException => e.printStackTrace()
    }
  }
}
