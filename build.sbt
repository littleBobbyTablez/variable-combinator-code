name := "variable-combinator"

version := "0.2"

scalaVersion := "2.13.3"


libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.0",
  "io.chrisdavenport" %% "log4cats-slf4j" % "1.0.1",
  "io.chrisdavenport" %% "cormorant-core" % "0.3.0-M1",
  "io.chrisdavenport" %% "cormorant-generic" % "0.3.0-M1",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test)

enablePlugins(JavaAppPackaging)