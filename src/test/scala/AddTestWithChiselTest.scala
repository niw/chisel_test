import chisel3._
import chisel3.tester.experimental.TestOptionBuilder.ChiselScalatestOptionBuilder
import chiseltest._
import chiseltest.internal.VerilatorBackendAnnotation
import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

// NOTE: `chiseltest` (`chisel-tester2`) is currently alpha.
// `chisel3.iotesters` (`chisel-testers`) may be better for testing.
// See `AddTest.scala` as well.

class AddTestWithChiselTest extends FunSuite with Matchers with ChiselScalatestTester {
  private def testAdd(c: Add) {
    val rnd = new Random()
    rnd.setSeed(0L)
    for (_ <- 0 until 10) {
      val a = rnd.nextInt(1 << c.w)
      val b = rnd.nextInt(1 << c.w)
      val carryIn = rnd.nextInt(1)
      c.io.a.poke(a.asUInt)
      c.io.b.poke(b.asUInt)
      c.io.carryIn.poke(carryIn.asUInt)
      c.io.sum.expect(((a + b + carryIn) & ((1 << c.w) - 1)).asUInt)
      c.io.carryOut.expect(((a + b + carryIn) >> c.w).asUInt)
      c.clock.step()
    }
  }

  test("Add should add inputs") {
    test(new Add(4))(testAdd)
  }

  test("Add should add inputs with Verilator") {
    test(new Add(4)).withAnnotations(Seq(VerilatorBackendAnnotation))(testAdd)
  }
}
