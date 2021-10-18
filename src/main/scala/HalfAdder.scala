import chisel3._

class HalfAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val sum = Output(UInt(1.W))
    val carryOut = Output(UInt(1.W))
  })

  io.sum := io.a(0) ^ io.b(0)
  io.carryOut := io.a(0) & io.b(0)
}
