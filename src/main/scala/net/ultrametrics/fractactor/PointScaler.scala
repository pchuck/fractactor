package net.ultrametrics.fractactor

/**
 * Ability to scale a point value with some transformation function.
 */
trait PointScaler {
  /**
   * Transform a point.
   * @param value the initial point
   * @return the scaled point
   */
  def transform(value: Int) = value
}
