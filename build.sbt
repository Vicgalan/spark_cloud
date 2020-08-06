name := "spark_cloud"

version := "0.1"

scalaVersion := "2.11.12"

mainClass in Compile := Some("RunCode")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.2.0",
  "org.apache.spark" %% "spark-core" % "2.2.0",
  "org.apache.spark" %% "spark-hive" % "2.4.3"
)
