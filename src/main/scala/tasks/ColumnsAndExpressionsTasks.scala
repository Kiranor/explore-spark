package tasks

import org.apache.spark.sql.SparkSession

object ColumnsAndExpressionsTasks extends App {

  val spark = SparkSession.builder().appName("DFTask3").config("spark.master", "local").getOrCreate()

  /**
   * Задание 3
   *
   * 1. Прочитать DF с фильмами (movies.json) and выбрать 2 любые колонки
   * 2. Создать новую колонку, котороя считает суммарный доход со всех фильмов US_Gross + Worldwide_Gross + DVD sales
   * 3. Выбрать все комедии (COMEDY) с рейтингом (IMDB) выше 6
   *
   * Можно использовать несколько разных вариантов реализации
   */

}
