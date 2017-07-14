lazy val commonSettings = Seq(
  name := "SlackSMACK",
  version := "0.1-SNAPSHOT",
  organization := "de.codecentric",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("TfIdfProcessorDF")
    // more settings here ...

  )


// META-INF discarding
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.1.1" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.1.1" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.1.1" % "provided",
  "org.json4s" % "json4s-native_2.11" % "3.5.2")




