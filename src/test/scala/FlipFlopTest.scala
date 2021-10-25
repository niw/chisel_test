import chisel3._
import chisel3.iotesters.SteppedHWIOTester
import chisel3.testers.TesterDriver
import firrtl.transforms.DontCheckCombLoopsAnnotation
import org.scalatest.{FunSuite, Matchers}

class FlipFlopTest extends FunSuite with Matchers {
  test("FlipFlop should remember data") {
    // To use `annotations`, we can't use `assertTesterPasses` by `ChiselRunners`.
    assert(TesterDriver.execute(() => new SteppedHWIOTester {
      val device_under_test = Module(new FlipFlop)
      private val c = device_under_test

      poke(c.io.data, 0)
      poke(c.io.enable, 1)

      step(1)
      expect(c.io.q, 0)

      step(1)
      expect(c.io.q, 0)

      poke(c.io.data, 1)

      step(1)
      expect(c.io.q, 1)

      step(1)
      expect(c.io.q, 1)
    }, annotations = Seq(DontCheckCombLoopsAnnotation)))
  }

  test("FlipFlop should not remember data without enable") {
    // To use `annotations`, we can't use `assertTesterPasses` by `ChiselRunners`.
    assert(TesterDriver.execute(() => new SteppedHWIOTester {
      val device_under_test = Module(new FlipFlop)
      private val c = device_under_test

      poke(c.io.data, 0)
      poke(c.io.enable, 0)

      step(1)
      expect(c.io.q, 0)

      step(1)
      expect(c.io.q, 0)

      poke(c.io.data, 1)

      step(1)
      expect(c.io.q, 0)

      step(1)
      expect(c.io.q, 0)
    }, annotations = Seq(DontCheckCombLoopsAnnotation)))
  }
}
