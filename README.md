Chisel Test
===========

A simple project for myself to learn Chisel and FPGA.

Supports [TinyFPGA BX](https://github.com/tinyfpga/TinyFPGA-BX) and [OrangeCrab](https://github.com/orangecrab-fpga/orangecrab-hardware) boards.

Build tool are required separately.

- [sbt](https://www.scala-sbt.org/)
- [yosys](https://github.com/YosysHQ/yosys)
- [icestorm](https://github.com/YosysHQ/icestorm) (for TinyFPGA BX, iCE40)
- [prjtrellis](https://github.com/YosysHQ/prjtrellis) (for OrangeCrab, ECP5)
- [nextpnr](https://github.com/YosysHQ/nextpnr)
- [tinyprog](https://github.com/tinyfpga/TinyFPGA-Bootloader/tree/master/programmer) (for TinyFPGA BX)
- [dfu-util](http://dfu-util.sourceforge.net/) (for OrangeCrab)

Usage
-----

To build a bitstream for a specific top module, simply `make` with `TOP_MODULE`.
Where `BOARD` can be `tinyfpgabx` or `orangecrab`.

```
make BOARD=... TOP_MODULE=...
```

For TinyFPGA BX, to program bitstream, connect device to USB then use `prog` task.

```
make BOARD=tinyfpgabx TOP_MODULE=... prog
```

For OrangeCrab, to dfu bitstream, connect device to USB, then use `dfu` task.

```
make BOARD=orangecrab TOP_MODULE=... dfu
```
