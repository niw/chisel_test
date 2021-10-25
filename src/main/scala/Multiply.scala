import chisel3._
import chisel3.util.{Cat, Fill}

class Multiply(val w: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(w.W))
    val b = Input(UInt(w.W))
    val result = Output(UInt((w * 2).W))
  })

  /*
   *       0110 (6)
   *  x    0011 (3)
   *  ---------
   *       0110 <- 0110 & 1
   *      0110  <- 0110 & 1
   *     0000   <- 0110 & 0
   *  + 0000    <- 0110 & 0
   *  ---------
   *   00010010 (18)
   */

  private val multipliedValues = VecInit {
    for (i <- 0 until w) yield io.a & Fill(w, io.b(i))
  }

  private val addIOs = VecInit(Seq.fill(w) {
    Module(new Add(w)).io
  })
  addIOs(0).a := 0.U
  addIOs(0).b := multipliedValues(0)
  addIOs(0).carryIn := 0.U
  for (i <- 1 until w) {
    addIOs(i).a := Cat(addIOs(i - 1).carryOut, addIOs(i - 1).sum(w - 1, 1))
    addIOs(i).b := multipliedValues(i)
    addIOs(i).carryIn := 0.U
  }

  io.result := Cat(addIOs(w - 1).carryOut, Cat(addIOs(w - 1).sum(w - 1, 1), Cat(for (i <- w - 1 to 0 by -1) yield addIOs(i).sum(0))))
}
