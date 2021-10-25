import chisel3._
import chisel3.iotesters.{ChiselRunners, SteppedHWIOTester}
import org.scalatest.{FunSuite, Matchers}

class SubtractTest extends FunSuite with Matchers with ChiselRunners {
  test("Subtract should subtract inputs") {
    assertTesterPasses(new SteppedHWIOTester {
      val device_under_test = Module(new Subtract(4))
      private val c = device_under_test

      rnd.setSeed(0L)
      for (_ <- 0 until 10) {
        val a = rnd.nextInt(1 << c.w)
        val b = rnd.nextInt(1 << c.w)
        poke(c.io.a, a)
        poke(c.io.b, b)
        expect(c.io.result, (a - b) & ((1 << c.w) - 1))
        step(1)
      }
    })
  }
}
