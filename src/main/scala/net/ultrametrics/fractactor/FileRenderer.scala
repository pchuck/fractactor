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

  def set(x: Int, y: Int, color: Int) = image.setRGB(x, y, color*Settings.Scale)

  def save() = {
    try
      ImageIO.write(image, "jpg", new File(fileName))
    catch {
      case e:IOException => e.printStackTrace()
    }
  }
}
