import chisel3._

class Toggle extends Module {
  val io = IO(new Bundle {
    val toggle = Input(Bool())
    val result = Output(Bool())
  })

  private val result = RegInit(false.B)
  when (Debounce(io.toggle)) {
    result := !result
  }

  io.result := result
}
