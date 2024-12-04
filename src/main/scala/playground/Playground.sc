import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.postgresql.Driver

import java.util.Properties

// Регистрируем драйвер для общения с внешними БД, в данном случае с Postgres, который у нас развернут в Docker

Class.forName("org.postgresql.Driver")

// Создаем объект сессии
implicit val spark: SparkSession = SparkSession.builder()
  .appName("Playground")
  .config("spark.master", "local")
  .getOrCreate()

implicit val sc: SparkContext = spark.sparkContext
import spark.implicits._

spark.sparkContext.setLogLevel("ERROR")
// Можем перейти в Spark UI по адресу http://localhost:4040/jobs/