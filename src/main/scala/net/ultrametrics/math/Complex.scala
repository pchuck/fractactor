package net.ultrametrics.math

/**
 * A complex number
 */
class Complex(val re: Double, val im: Double) {
  def + (that: Complex) =
    new Complex(re + that.re, im + that.im)
  def - (that: Complex) =
    new Complex(re - that.re, im - that.im)
  def * (that: Complex) =
    new Complex(re * that.re - im * that.im,
                re * that.im + im * that.re)
  def / (that: Complex) = {
    val denom = that.re * that.re + that.im * that.im
    new Complex((re * that.re + im * that.im) / denom,
                (im * that.re - re * that.im) / denom)
  }

  def scalar (): Double = (re * re + im * im).abs

  override def toString =
    re + (if (im < 0) "-" + (-im) else "+" + im) + "i"

  override def equals(that: Any): Boolean = 
    that match {
      case that: Complex =>
        re == that.re && im == that.im
      case _ => false
    }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Complex]

  def main(args: Array[String]) {
    val x = new Complex(2, 1); val y = new Complex (1, 3)
    printf("(%s)+(%s)=(%s)", x, y, x + y);
  }
}
