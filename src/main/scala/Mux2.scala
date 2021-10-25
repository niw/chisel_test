import chisel3._

class Mux2 extends Module {
  val io = IO(new Bundle {
    val selector = Input(Bool())
    val a = Input(Bool())
    val b = Input(Bool())
    val result = Output(Bool())
  })

  io.result := (io.selector & io.a) | ((~io.selector) & io.b)
}
