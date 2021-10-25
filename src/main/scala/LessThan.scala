import chisel3._

class LessThan(val w: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(w.W))
    val b = Input(UInt(w.W))
    val result = Output(Bool())
  })

  private val subtract = Module(new Subtract(w))
  subtract.io.a := io.a
  subtract.io.b := io.b

  // If A and B are the both positive or negative, use subtracted result.
  // If A and B are different, since negative - positive is always negative,
  // and positive - negative is always positive, use A as result.
  private val sign_bit = w - 1
  private val mux2 = Module(new Mux2())
  mux2.io.selector := io.a(sign_bit) === io.b(sign_bit)
  mux2.io.a := subtract.io.result(sign_bit)
  mux2.io.b := io.a(sign_bit)
  io.result := mux2.io.result
}
