package tasks

import org.apache.spark.sql.SparkSession

object AggregationsTasks extends App {

  val spark = SparkSession.builder().appName("DFTask4").config("spark.master", "local").getOrCreate()

  /**
   * Задание 4
   *
   * 1. Посчитать суммарный доход с фильмов по всем параметрам Total_Gross = US_Gross + Worldwide_Gross" + US_DVD_Sales
   * 2. Посчитать, сколько есть уникальных режиссеров в предоставленном наборе данных
   * 3. Показать среднее (mean) and стандартное отклонение (standard deviation) по колонке US_Gross
   * 4. Посчитать средний рейтинг (IMDB) и среднее US_Gross для каждого режиссера
   */

}
