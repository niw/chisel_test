import chisel3._

class TwoBitsAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(2.W))
    val b = Input(UInt(2.W))
    val sum = Output(UInt(2.W))
    val carryOut = Output(UInt(1.W))
  })

  private val adder = Module(new Adder(2))
  adder.io.a := io.a
  adder.io.b := io.b
  adder.io.carryIn := 0.asUInt()
  io.sum := adder.io.sum
  io.carryOut := adder.io.carryOut
}
