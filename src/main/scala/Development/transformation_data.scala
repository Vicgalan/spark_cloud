package Development

import Commons.Utils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

class transformations_data(utils: Utils) {

  //Suppress Spark output
  Logger.getLogger("org").setLevel(Level.ERROR)

  import utils.spark.implicits._

  def action (): Unit = {

    /** Reading data and transformation data*/

    val input : DataFrame = utils.readTable("inputs.input")


    def maxDayBday(df: DataFrame)  = {
      val maxDayBdayValue = df.groupBy("bday_day").agg(count("bday_day"))
        .agg(max("count(bday_day)")).first()(0)
      maxDayBdayValue
    }

    def minMonthBday(df: DataFrame)  = {
      df.groupBy("bday_month").agg(count("bday_month").alias("total"))
        .orderBy("total").first()(0)
    }

    val maxDayBdayValue = maxDayBday(input)
    val minMonthBdayValue = minMonthBday(input)


    /** Post by email providers */

    val postByEmail = input.groupBy("email").agg(sum("posts").alias("Total_Posts"))

    /** Year/s with max sign ups */
    val yearMaxSingUp = input.select(to_date(from_unixtime($"joined")).alias("date"))
      .withColumn("year",substring($"date",1,4))
      .groupBy("year").agg(count("year").alias("yearMaxSingUp")).first()(0)


    /** Here I got the octet of the Ip Adresses but I don't really understand the requirements */

    val st_octet =  input.withColumn("1st_octet", split($"ip_address","\\.").getItem(0))

    val octetip =  input.withColumn("1st_octet", split($"ip_address","\\.").getItem(0))
      .withColumn("2nd_octet", split($"ip_address","\\.").getItem(1))
      .withColumn("3rd_octet", split($"ip_address","\\.").getItem(2))


    /** Number of referral by members */

    val referalByMembers = input.groupBy("member_id").agg(sum("referred_by").alias("Total_Referals"))

    val outputInfo = input.filter($"posts" > 1)
      .join(postByEmail, Seq("email"),"inner")
      .join(referalByMembers,Seq("member_id"), "inner")
      .withColumn("maxDayBday", lit(maxDayBdayValue))
      .withColumn("minMonthBday", lit(minMonthBdayValue))
      .withColumn("yearMaxSingUp", lit(yearMaxSingUp))


    /** Write the table */

    utils.writeHiveTable(outputInfo,"reporting.outputInfo")

    //utils.writeS3Table(outputInfo,"s3a://victortestOrange/testOrange")


  }

}
