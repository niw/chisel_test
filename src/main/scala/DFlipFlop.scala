import chisel3._

class DFlipFlop extends Module {
  val io = IO(new Bundle {
    val data = Input(Bool())
    val q = Output(Bool())
  })

  private val dLatch1 = Module(new DLatch)
  dLatch1.io.data := io.data
  dLatch1.io.enable := ~clock.asBool()

  private val dLatch2 = Module(new DLatch)
  dLatch2.io.data := dLatch1.io.q
  dLatch2.io.enable := clock.asBool()

  io.q := dLatch2.io.q
}
