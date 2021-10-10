name := "chisel_test"

version := "0.1.0"

// Chisel3 3.4.0 requires Scala 2.12, 3.5.0 will support Scala 2.13.
scalaVersion := "2.12.15"
// See <https://github.com/freechipsproject/chisel-template/blob/release/build.sbt#L7>
scalacOptions += "-Xsource:2.11"

libraryDependencies += "edu.berkeley.cs" %% "chisel3" % "3.4.0"
libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.3.0" % "test"
