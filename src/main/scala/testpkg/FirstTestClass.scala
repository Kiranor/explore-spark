package testpkg

import org.apache.spark.sql.SparkSession

object FirstTestClass extends App {
  val spark = SparkSession.builder()
    .appName("test app")
    .config("spark.master", "local")
    .getOrCreate()

  val testDF = spark.read.option("inferSchema", "true").json("src/main/resources/data/test.json")

  testDF.show(false)
}
