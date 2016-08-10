organization := "info.rgomes"

name := "ringbuffer"

scalaVersion := "2.11.8"

libraryDependencies ++=
  Seq(
    "net.jcip"    %  "jcip-annotations" % "1.0",
    "com.lihaoyi" %% "utest"            % "0.4.3" % "test")

testFrameworks += new TestFramework("utest.runner.Framework")
