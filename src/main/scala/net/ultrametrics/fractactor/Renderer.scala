package net.ultrametrics.fractactor

/**
 * Traits of a class capable of rendering an image (to disk, screen, etc)
 */
trait Renderer
{
  /**
   * Create the image.
   * @param width the width in pixels
   * @param height the height in pixels
   */
  def create(width: Int, height: Int) 

  /**
   * Set a pixel in the image.
   * @param x the x-coordinate
   * @param y the y-coordinate
   * @param color the color of the pixel
   */
  def set(x: Int, y: Int, color: Int) 

  /**
   * Persist the image
   */
  def save()
}
