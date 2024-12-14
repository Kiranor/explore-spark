package tasks

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, expr}
import tasks.DataSourceTasks.spark

object ColumnsAndExpressionsTasks extends App {

  val spark = SparkSession.builder().appName("DFTask3").config("spark.master", "local").getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._

  /**
   * Задание 3
   *
   * 1. Прочитать DF с фильмами (movies.json) и выбрать 2 любые колонки
   * 2. Создать новую колонку, которая считает суммарный доход со всех фильмов US_Gross + Worldwide_Gross + DVD sales
   * 3. Выбрать все комедии (COMEDY) с рейтингом (IMDB) выше 6
   *
   * Можно использовать несколько разных вариантов реализации
   */
  val moviesDF = spark.read.json("src/main/resources/data/movies.json")

  moviesDF.select("Title", "Release_Date")

  moviesDF.select("Title", "US_Gross", "Worldwide_Gross")
    .withColumn("Total_Gross", col("US_Gross") + col("Worldwide_Gross"))
    .show(false)

  moviesDF.select("Title", "IMDB_Rating")
    .where("Major_Genre = 'Comedy' and IMDB_Rating > 6")
    .show(false)
}
