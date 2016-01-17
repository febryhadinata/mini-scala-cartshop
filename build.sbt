name := """mini-scala-cartshop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.5" // or "2.10.4"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "com.typesafe.play" %% "play-slick" % "0.8.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
// routesGenerator := InjectedRoutesGenerator

fork in Test := false
fork in run := true


fork in run := true