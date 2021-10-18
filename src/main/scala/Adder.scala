import chisel3._

class Adder(n: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(n.W))
    val b = Input(UInt(n.W))
    val carryIn = Input(UInt(1.W))
    val sum = Output(UInt(n.W))
    val carryOut = Output(UInt(1.W))
  })

  private val fullAdders = VecInit(Seq.fill(n) {
    Module(new FullAdder).io
  })

  private val carries = Wire(Vec(n + 1, UInt(1.W)))

  private val sums = Wire(Vec(n, UInt(1.W)))

  carries(0) := io.carryIn
  for (i <- 0 until n) {
    val fullAdder = fullAdders(i)
    fullAdder.a := io.a(i)
    fullAdder.b := io.b(i)
    fullAdder.carryIn := carries(i)
    sums(i) := fullAdder.sum
    carries(i + 1) := fullAdder.carryOut
  }

  io.sum := sums.asUInt()
  io.carryOut := carries(n)
}
