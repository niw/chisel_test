import chisel3._

class And extends Module {
  val io = IO(new Bundle {
    val input_a = Input(Bool())
    val input_b = Input(Bool())
    val output = Output(Bool())
  })

  io.output := io.input_a & io.input_b
}
