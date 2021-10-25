import chisel3._

class Subtract(val w: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(w.W))
    val b = Input(UInt(w.W))
    val result = Output(UInt(w.W))
  })

  // Subtract B from A means add -B to A, -B in the bit representation is (~B + 1),
  // therefore it is A + ~B + 1 as `carryIn`.
  private val add = Module(new Add(w))
  add.io.a := io.a
  add.io.b := ~(io.b)
  add.io.carryIn := 1.U
  io.result := add.io.sum
}
