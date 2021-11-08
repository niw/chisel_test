import chisel3._
import chisel3.iotesters.SteppedHWIOTester
import chisel3.testers.TesterDriver
import firrtl.transforms.DontCheckCombLoopsAnnotation
import org.scalatest.{FunSuite, Matchers}

class RegisterTest extends FunSuite with Matchers {
  test("Register should remember data") {
    // To use `annotations`, we can't use `assertTesterPasses` by `ChiselRunners`.
    assert(TesterDriver.execute(() => new SteppedHWIOTester {
      val device_under_test = Module(new Register(4))
      private val c = device_under_test

      rnd.setSeed(0L)
      for (_ <- 0 until 10) {
        poke(c.io.data, 0)
        poke(c.io.enable, 1)

        step(1)
        expect(c.io.q, 0)

        step(1)
        expect(c.io.q, 0)

        val value = rnd.nextInt(1 << c.w)
        poke(c.io.data, value)

        step(1)
        expect(c.io.q, value)

        step(1)
        expect(c.io.q, value)
      }
    }, annotations = Seq(DontCheckCombLoopsAnnotation)))
  }
}
