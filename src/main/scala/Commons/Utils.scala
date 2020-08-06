package Commons

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

class Utils {

  val spark = org.apache.spark.sql.SparkSession.builder().enableHiveSupport().getOrCreate()

  /*
* Function that read a table
*/
  def readTable(tableName: String): DataFrame = {
    spark.read.table(tableName)
  }

  /*
* Function that creates a hive table
*/
  def writeHiveTable(df: DataFrame, tableName: String): Unit = {
    df.write.format("parquet").mode(SaveMode.Overwrite).saveAsTable(tableName)
  }

  /*
* Function that load s3 data
*/
  //  def writeS3Table(df: DataFrame, path: String): Unit = {
  //    df.write.parquet(path)
  //
  //  }

}
