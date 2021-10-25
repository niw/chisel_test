import chisel3._

class SRLatch extends Module {
  val io = IO(new Bundle {
    val set = Input(Bool())
    val reset = Input(Bool())

    val q = Output(Bool())
    val notQ = Output(Bool())
  })

  io.q := ~(io.reset | io.notQ)
  io.notQ := ~(io.set | io.q)
}
