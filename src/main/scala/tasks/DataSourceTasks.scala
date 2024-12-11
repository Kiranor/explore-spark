package tasks

import org.apache.spark.sql.SparkSession

object DataSourceTasks extends App {

  val spark = SparkSession.builder().appName("DFTask2").config("spark.master", "local").getOrCreate()

  /**
   * Задание 2: прочитать DF data/movies.json и затем записать его как
   * - csv-файл с разделителем \t
   * - Parquet-файл
   */


}
