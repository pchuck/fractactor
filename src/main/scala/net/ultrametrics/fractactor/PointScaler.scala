package net.ultrametrics.fractactor

/** Point scaling functions.
 */
class PointScaler {
  /**
   * Scale a calculated point to 24-bit RGB space for screen display.
   */
  def iToRGB(iteration: Int) = iteration * Settings.Scale
}
