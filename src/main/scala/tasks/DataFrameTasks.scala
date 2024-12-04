package tasks

import org.apache.spark.sql.SparkSession

object DataFrameTasks extends App {

  val spark = SparkSession.builder().appName("DFTask1").config("spark.master", "local").getOrCreate()
  /**
   * Задание 1:
   * 1) Создать DF, содержащий информацию о смартфонах
   *   - make
   *   - model
   *   - screen dimension
   *   - camera megapixels
   *
   * 2) Прочитать файл data/movies.json
   *   - распечатать его схему
   *   - посчитать количество строк
   */


}
