package tasks

import org.apache.spark.sql.SparkSession
import sparkEssentials.DataFramesBasics.spark
import tasks.JdbcIntegrationTasks.spark

object DataFrameTasks extends App {

  val spark = SparkSession.builder().appName("DFTask1").config("spark.master", "local").getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._
  /**
   * Задание 1:
   * 1) Создать DF, содержащий информацию о смартфонах
   *   - make
   *   - model
   *   - screen dimension
   *   - camera megapixels
   */
  val smartphones = Seq(
    ("Samsung", "Galaxy S10", "Android", 12),
    ("Apple", "iPhone X", "iOS", 13),
    ("Nokia", "3310", "THE BEST", 0)
  )
  val smartphonesDF = smartphones.toDF("Make", "Model", "Platform", "CameraMegapixels")
  smartphonesDF.show()

  /*
   * 2) Прочитать файл data/movies.json
   *   - распечатать его схему
   *   - посчитать количество строк
   */

  val moviesDF = spark
    .read
    .format("json")
    .option("inferSchema", "true")
    .load("src/main/resources/data/movies.json")

  moviesDF.printSchema()
  println(s"The Movies DF has ${moviesDF.count()} rows")
}
