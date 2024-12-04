package tasks
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.postgresql.Driver

import java.util.Properties

object JdbcIntegrationTasks extends App {

  // Регистрируем драйвер для общения с внешними БД, в данном случае с Postgres, который у нас развернут в Docker

  Class.forName("org.postgresql.Driver")

  // Создаем объект сессии
  implicit val spark: SparkSession = SparkSession.builder()
    .appName(this.getClass.getSimpleName)
    .config("spark.master", "local")
    .getOrCreate()

  implicit val sc: SparkContext = spark.sparkContext
  import spark.implicits._

  val departmentsDF: DataFrame = spark.read.jdbc(
    url = "jdbc:postgresql://localhost:5432/explore_spark?user=docker&password=docker",
    table = "public.departments",
    properties = new Properties()
  )

  departmentsDF.show(false)
  //  spark.sparkContext.setLogLevel("ERROR")
  // Можем перейти в Spark UI по адресу http://localhost:4040
}