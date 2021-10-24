import chisel3._

class Add(val w: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(w.W))
    val b = Input(UInt(w.W))
    val carryIn = Input(UInt(1.W))
    val sum = Output(UInt(w.W))
    val carryOut = Output(UInt(1.W))
  })

  private val fullAdderIOs = VecInit(Seq.fill(w) {
    Module(new FullAdder).io
  })
  private val sums = Wire(Vec(w, UInt(1.W)))
  private val carries = Wire(Vec(w + 1, UInt(1.W)))

  carries(0) := io.carryIn
  for (i <- 0 until w) {
    val fullAdderIO = fullAdderIOs(i)
    fullAdderIO.a := io.a(i)
    fullAdderIO.b := io.b(i)
    fullAdderIO.carryIn := carries(i)
    sums(i) := fullAdderIO.sum
    carries(i + 1) := fullAdderIO.carryOut
  }

  io.sum := sums.asUInt()
  io.carryOut := carries(w)
}
