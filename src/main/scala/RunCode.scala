import Commons.Utils
import Development.transformations_data
import org.apache.log4j.{Level, Logger}

object RunCode {

  val utils = new Utils

  def main(args: Array[String]): Unit = {
    //Suppress Spark output
    Logger.getLogger("akka").setLevel(Level.ERROR)

    args match {

      case Array("-all") =>
        new transformations_data(utils).action()

      case _ =>
        println("ERROR: Invalid option")
        throw new IllegalArgumentException("Wrong arguments. Usage: \n" +
          "Options -all ")
    }

  }

}
