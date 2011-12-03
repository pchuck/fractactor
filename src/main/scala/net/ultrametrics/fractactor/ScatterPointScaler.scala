package net.ultrametrics.fractactor

/**
 * Map point values in 8-bit space to a 24-bit RGB colorspace.
 */
trait ScatterPointScaler extends PointScaler
{
  override def transform(value: Int) = value * Settings.Scale8to24Bit
}
