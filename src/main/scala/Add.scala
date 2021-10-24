import chisel3._

class Add(val w: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(w.W))
    val b = Input(UInt(w.W))
    val carryIn = Input(UInt(1.W))
    val sum = Output(UInt(w.W))
    val carryOut = Output(UInt(1.W))
  })

  private val sums = Wire(Vec(w, UInt(1.W)))
  private val carries = Wire(Vec(w + 1, UInt(1.W)))

  carries(0) := io.carryIn
  for (i <- 0 until w) {
    val fullAdder = Module(new FullAdder)
    fullAdder.io.a := io.a(i)
    fullAdder.io.b := io.b(i)
    fullAdder.io.carryIn := carries(i)
    sums(i) := fullAdder.io.sum
    carries(i + 1) := fullAdder.io.carryOut
  }

  io.sum := sums.asUInt()
  io.carryOut := carries(w)
}
