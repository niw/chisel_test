import chisel3._

class LeftShift(val w: Int) extends Module {
  val io = IO(new Bundle {
    val value = Input(UInt(w.W))
    val shiftAmount = Input(UInt(w.W))
    val result = Output(UInt(w.W))
  })

  private val result = Wire(Vec(w, UInt(1.W)))

  // This creates w^2 Mux2 modules.
  for (i <- 0 until w) {
    // This is chain of multiplexer represents case-multiplexer to select value for specific case, in this case, `shiftAmount`.
    // The result is `r(w)`.
    val r = Wire(Vec(w + 1, UInt(1.W)))
    // This is a default value for the case-multiplexer, since one of case should match, this value will not be used.
    r(0) := 0.U
    for (j <- 0 until w) {
      val mux2 = Module(new Mux2)
      mux2.io.selector := io.shiftAmount === j.asUInt()
      // This `io.value(i - j)` is left-shifted bit of input for i-th bit.
      mux2.io.a := (if (j > i) 0.U else io.value(i - j))
      mux2.io.b := r(j)
      r(j + 1) := mux2.io.result
    }
    result(i) := r(w)
  }

  io.result := result.asUInt()
}
