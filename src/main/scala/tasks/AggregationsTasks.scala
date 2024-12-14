package tasks

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, col, countDistinct, mean, stddev, sum}

object AggregationsTasks extends App {

  val spark = SparkSession.builder().appName("DFTask4").config("spark.master", "local").getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._
  val moviesDF = spark.read.json("src/main/resources/data/movies.json")
  /**
   * Задание 4
   *
   * 1. Посчитать суммарный доход с фильмов по всем параметрам Total_Gross = US_Gross + Worldwide_Gross + US_DVD_Sales
   * 2. Посчитать, сколько есть уникальных режиссеров в предоставленном наборе данных
   * 3. Показать среднее (mean) and стандартное отклонение (standard deviation) по колонке US_Gross
   * 4. Посчитать средний рейтинг (IMDB) и среднее US_Gross для каждого режиссера
   */

  moviesDF
    .select((col("US_Gross") + col("Worldwide_Gross") + col("US_DVD_Sales")).as("Total_Gross"))
    .select(sum("Total_Gross"))
    .show()

  moviesDF
    .select(countDistinct(col("Director")))
    .show()

  moviesDF.select(
    mean("US_Gross"),
    stddev("US_Gross"))
    .show()

  moviesDF
    .groupBy("Director")
    .agg(
      avg("IMDB_Rating").as("Avg_Rating"),
      sum("US_Gross").as("Total_US_Gross")
    )
    .orderBy(col("Avg_Rating").desc_nulls_last)
    .show()

}
