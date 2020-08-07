name := "spark_cloud"

version := "0.1"

scalaVersion := "2.11.12"

fork in Test := true
javaOptions ++= Seq("-Xms256M", "-Xmx1024M", "-XX:MaxPermSize=1024M", "-XX:+CMSClassUnloadingEnabled")

mainClass in Compile := Some("RunCode")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.2.0" % "provided",
  "org.apache.spark" %% "spark-core" % "2.2.0" % "provided",
  "org.apache.spark" %% "spark-hive" % "2.4.3" % "provided",
  "com.holdenkarau" %% "spark-testing-base" % "1.5.1_0.2.1" % Test,
  "com.typesafe" % "config" % "1.3.1",
  "org.scalatest" %% "scalatest" % "2.2.2" % Test
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.withClassifier(`classifier` = Some("assembly"))
}