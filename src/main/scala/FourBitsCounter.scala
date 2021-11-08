import chisel3._

class FourBitsCounter extends Module {
  val io = IO(new Bundle {
    val result = Output(UInt(4.W))
  })

  private val clockCount = RegInit(0.U(32.W))
  private val count = RegInit(0.U(4.W))

  // Input clock is 16Mhz for TinyFPGA BX
  when (clockCount === 16000000.U) {
    count := count + 1.U
    clockCount := 0.U
  }.otherwise {
    clockCount := clockCount + 1.U
  }

  io.result := count
}
