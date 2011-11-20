package net.ultrametrics.fractactor

import javax.swing.JFrame
import javax.swing.JScrollPane

/**
 * Screen-based image renderer, backed by a view window.
 */
class ScreenRenderer(val width: Int, val height: Int) extends Renderer
{
  var panel = new RenderPanel()
  var frame = new JFrame

  def create(width: Int, height: Int) = { 
    frame.add(new JScrollPane(panel))
    frame.setSize(width, height)
    frame.setVisible(true)
  }

  def set(x: Int, y: Int, color: Int) = panel.drawPixel(x, y, color)

  def save() = { }
}
