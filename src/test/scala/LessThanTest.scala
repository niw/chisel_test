import chisel3._
import chisel3.iotesters.{ChiselRunners, SteppedHWIOTester}
import org.scalatest.{FunSuite, Matchers}

class LessThanTest extends FunSuite with Matchers with ChiselRunners {
  private implicit class IntWithWidth(i: Int) {
    def withWidth(w: Int): Int = {
      val sign_bit = w - 1
      val unsignedValueMask = (1 << sign_bit) - 1
      val isNegative = (i >> sign_bit) == 1
      // if the value is negative, fill the remaining significant bits with 1 to extend width to Int.
      (i & unsignedValueMask) | (if (isNegative) -1 & ~unsignedValueMask else 0)
    }
  }

  test("LessThan should compare inputs") {
    assertTesterPasses(new SteppedHWIOTester {
      val device_under_test = Module(new LessThan(4))
      private val c = device_under_test

      rnd.setSeed(0L)
      for (_ <- 0 until 10) {
        val a = rnd.nextInt(1 << c.w)
        val b = rnd.nextInt(1 << c.w)
        poke(c.io.a, a)
        poke(c.io.b, b)
        expect(c.io.result, a.withWidth(c.w) < b.withWidth(c.w))
        step(1)
      }
    })
  }
}
