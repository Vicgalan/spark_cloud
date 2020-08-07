package com

import Development.transformations_data
import Commons.Utils
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{LogManager, Logger}
import org.scalatest.FunSuite

class test extends FunSuite {

  sys.props("testing") = "true"

  val log: Logger = LogManager.getRootLogger
  val logCfgProps : Config =  ConfigFactory.load("log4j.properties")

  SparkSession.builder().master("local").appName("spark session").getOrCreate()


  val ut = new Utils
  val spark: SparkSession = ut.spark

  val tranformationsTests = new transformations_data(ut)

  val path_input_data = "src/test/resources/input.csv"

  val input_table = spark.read
    .format("csv")
    .option("header", "true") //first line in file has headers
    .option("delimiter", ",")
    .load(path_input_data)

  //tranformationsTests.action()

  test("maxDayBday should be 20") {
    assert(tranformationsTests.maxDayBday(input_table) === 20)
  }

  test("minMonthBday should be 3") {
    assert(tranformationsTests.minMonthBday(input_table) === "3")
  }

  test("datafram size"){
    assert(tranformationsTests.transformationsData(input_table).schema.size === 10)
  }


}
