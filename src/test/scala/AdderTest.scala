import chisel3._
import chisel3.iotesters.{ChiselRunners, SteppedHWIOTester}
import org.scalatest.{FunSuite, Matchers}

class AdderTest extends FunSuite with Matchers with ChiselRunners {
  test("Adder adds inputs") {
    assertTesterPasses(new SteppedHWIOTester {
      val device_under_test = Module(new Adder(2))
      private val c = device_under_test

      rnd.setSeed(0L)
      for (_ <- 0 until 10) {
        val a = rnd.nextInt(1 << c.w)
        val b = rnd.nextInt(1 << c.w)
        val carryIn = rnd.nextInt(1)
        poke(c.io.a, a)
        poke(c.io.b, b)
        poke(c.io.carryIn, carryIn)
        expect(c.io.sum, (a + b + carryIn) & ((1 << c.w) - 1))
        expect(c.io.carryOut, (a + b + carryIn) >> c.w)
        step(1)
      }
    })
  }
}
