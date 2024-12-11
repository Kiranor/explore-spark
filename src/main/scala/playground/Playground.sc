import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.{avg, col, desc, round}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.postgresql.Driver

import java.util.Properties

object DatabaseUtils {
    private val databasePwd = "docker"
    private val databaseUser = "docker"
    private val databaseName = "explore_spark"
    private val databaseHost = "localhost:5432"
    private val databaseUrl = s"jdbc:postgresql://$databaseHost/$databaseName?user=$databaseUser&password=$databasePwd"

    def readTable(tableNameWithSchemaCommaSeparated: String)(implicit spark: SparkSession): DataFrame = spark.read.jdbc(
        url = this.databaseUrl,
        table = tableNameWithSchemaCommaSeparated,
        properties = new Properties()
    )
}

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

val titlesDF: DataFrame = DatabaseUtils.readTable("public.titles")
val salariesDF: DataFrame = DatabaseUtils.readTable("public.salaries")

titlesDF.createOrReplaceTempView("titles")
salariesDF.createOrReplaceTempView("salaries")

