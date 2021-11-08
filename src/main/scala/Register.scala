import chisel3._

class Register(val w: Int) extends Module {
  val io = IO(new Bundle {
    val data = Input(UInt(w.W))
    val enable = Input(Bool())
    val q = Output(UInt(w.W))
  })

  private val q = Wire(Vec(w, UInt(1.W)))

  for (i <- 0 until w) {
    val ff = Module(new FlipFlop())
    ff.io.data := io.data(i)
    ff.io.enable := io.enable
    q(i) := ff.io.q
  }

  io.q := q.asUInt()
}
