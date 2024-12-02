package tasks

import org.apache.spark.sql.SparkSession

object JoinsTasks extends App {

  val spark = SparkSession.builder().appName("DFTask5").config("spark.master", "local").getOrCreate()

  /**
   * Задание 5
   *
   * 1. показать всех работников и из максимальные зарплаты
   * 2. показать всех работников, которые никогда не были менеджерами
   * 3. найти наименования позиции лучших 10 сотрудников в компании
   */

}
