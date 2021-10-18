import chisel3._

class FullAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val carryIn = Input(UInt(1.W))
    val sum = Output(UInt(1.W))
    val carryOut = Output(UInt(1.W))
  })

  private val halfAdder = Module(new HalfAdder)
  halfAdder.io.a := io.a
  halfAdder.io.b := io.b

  private val halfAdderCarry = Module(new HalfAdder)
  halfAdderCarry.io.a := halfAdder.io.sum
  halfAdderCarry.io.b := io.carryIn

  io.sum := halfAdderCarry.io.sum
  io.carryOut := halfAdder.io.carryOut | halfAdderCarry.io.carryOut
}
