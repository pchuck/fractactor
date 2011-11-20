package net.ultrametrics.fractactor

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel


class RenderPanel() extends JPanel 
{
  override def paintComponent(g: Graphics) {
//    size.setSize(image.getWidth(), image.getHeight())
  }

  def drawScanline(width: Int, y: Int, scanline: Array[Int]) {
    var g = getGraphics()
    for(x <- 0 until width) {
      g.setColor(new Color(scanline(x) * Settings.Scale))
      g.fillRect(x, y, 1, 1)
    }
  }

  def drawPixel(x: Int, y: Int, color: Int) {
    var g = getGraphics()
    g.setColor(new Color(color * Settings.Scale))
    g.fillRect(x, y, 1, 1)
  }
}
