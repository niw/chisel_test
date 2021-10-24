import chisel3._

class TwoBitsAdd extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(2.W))
    val b = Input(UInt(2.W))
    val sum = Output(UInt(2.W))
    val carryOut = Output(UInt(1.W))
  })

  private val add = Module(new Add(2))
  add.io.a := io.a
  add.io.b := io.b
  add.io.carryIn := 0.asUInt()
  io.sum := add.io.sum
  io.carryOut := add.io.carryOut
}
