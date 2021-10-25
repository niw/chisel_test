import chisel3._

class DLatch extends Module {
  val io = IO(new Bundle {
    val data = Input(Bool())
    val enable = Input(Bool())

    val q = Output(Bool())
  })

  private val srLatch = Module(new SRLatch)
  srLatch.io.set := io.data & io.enable
  srLatch.io.reset := ~io.data & io.enable

  io.q := srLatch.io.q
}
