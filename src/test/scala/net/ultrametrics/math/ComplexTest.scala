package net.ultrametrics.math

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Complex number test
 */
class ComplexSpec extends FlatSpec with ShouldMatchers {
  val c1_3 = new Complex(1, 3)
  val c2_1 = new Complex(2, 1)
  val c0_0 = new Complex(0, 0)
  val c1_3_alt = new Complex(1, 3)

  "Like Complex numbers" should "be equal" in {
    c1_3 should equal(c1_3_alt)
  }

  "Like Complex numbers" should "be equivalent" in {
    c1_3 == c1_3_alt
  }
    
  "Complex numbers" should "sum" in {
    val r = c1_3 + c2_1

    // a few different ways to express the results..
    //    r == (c1_3 + c2_1)
    //    r == new Complex(3, 4);
    //r.toString should equal("3.0+5.0i")

    r should equal(new Complex(3, 4))
  }

  "Complex numbers" should "multiply" in {
    val r = c1_3 * c2_1
    r == new Complex(-1, 7)
  }

  "Complex numbers" should "yield NaN on divide by zero" in {
    val r = c1_3 / c0_0
    r.toString should equal("NaN+NaNi")
  }
}
