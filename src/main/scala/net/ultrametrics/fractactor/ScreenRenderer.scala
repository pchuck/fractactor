package net.ultrametrics.fractactor

import scala.swing._
import swing.event.WindowClosing

/**
 * Screen-based image renderer, backed by a view window.
 */
class ScreenRenderer(val width: Int, val height: Int) extends Renderer
{
  val panel = new RenderPanel
  val mainFrame = new MainFrame {
      title = "Fractal Generator"
      contents = panel
      reactions += {
        case WindowClosing(e) => System.exit(0)
      }
    }

  def create(width: Int, height: Int) = { 
    panel.create(width, height)
    mainFrame.preferredSize = new Dimension(width, height)
    mainFrame.pack
    mainFrame.visible = true
  }

  def set(x: Int, y: Int, color: Int) = {
    panel.drawPixel(x, y, color)
    mainFrame.repaint // not very efficient, but illustrates actor progress
  }

  def save() = { }
}
