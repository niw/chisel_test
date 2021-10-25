import chisel3._

class FlipFlop extends Module {
  val io = IO(new Bundle {
    val data = Input(Bool())
    val enable = Input(Bool())
    val q = Output(Bool())
  })

  private val dFlipFlip = Module(new DFlipFlop)
  private val mux = Module(new Mux2)
  mux.io.selector := io.enable
  mux.io.a := io.data
  mux.io.b := dFlipFlip.io.q
  dFlipFlip.io.data := mux.io.result & ~reset.asBool()
  io.q := dFlipFlip.io.q
}
