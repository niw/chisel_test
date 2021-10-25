import chisel3._
import chisel3.iotesters.{ChiselRunners, SteppedHWIOTester}
import org.scalatest.{FunSuite, Matchers}

class LeftShiftTest extends FunSuite with Matchers with ChiselRunners {
  test("LeftShift should add inputs") {
    assertTesterPasses(new SteppedHWIOTester {
      val device_under_test = Module(new LeftShift(4))
      private val c = device_under_test

      rnd.setSeed(0L)
      for (_ <- 0 until 10) {
        val value = rnd.nextInt(1 << c.w)
        val shiftAmount = rnd.nextInt(c.w)
        poke(c.io.value, value)
        poke(c.io.shiftAmount, shiftAmount)
        expect(c.io.result, (value << shiftAmount) & ((1 << c.w) - 1))
        step(1)
      }
    })
  }
}
