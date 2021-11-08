import chisel3._
import chisel3.util.{Counter, RegEnable}

class Debounce extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(Bool())
  })

  // 100ms at 16Mhz clock
  private val (_, enable) = Counter(true.B, 1600000)

  private val current = RegEnable(io.in, false.B, enable)
  private val previous = RegEnable(current, false.B, enable)

  io.out := current && !previous && enable
}

object Debounce {
  def apply(in: Bool): Bool = {
    val debounce = Module(new Debounce)
    debounce.io.in := in
    debounce.io.out
  }
}
