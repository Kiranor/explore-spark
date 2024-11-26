package playground

import org.apache.spark.sql.SparkSession

object Playground extends App {

  val spark = SparkSession.builder()
    .appName("Spark Essentials Playground App")
    .config("spark.master", "local")
    .getOrCreate()
}
