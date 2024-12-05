package tasks
import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.{avg, col, count, desc, round}
import org.apache.spark.sql.{DataFrame, RelationalGroupedDataset, SparkSession}
import org.postgresql.Driver
import tasks.JdbcIntegrationTasks.spark

import java.util.Properties

// Создадим объект, содержащий сведения о параметрах подключения к БД, для последующей простоты использования
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

  // Можем перейти в Spark UI по адресу http://localhost:4040
  // Для меньшего количества сообщений в логе от spark можно снизить уровень логирования следующей командой
  spark.sparkContext.setLogLevel("ERROR")

  /*  Задание 1. Определить число работников мужского и женского пола.*/
  val employeesDF: DataFrame = DatabaseUtils.readTable("public.employees")

  employeesDF.createOrReplaceTempView("employees")

  spark.sql(
    """
      |select gender, count(gender) as gender_count
      |from employees
      |group by gender
      |order by gender_count desc
      |""".stripMargin)
    .show()

  employeesDF
    .groupBy(col("gender"))
    .count()
    .withColumnRenamed("count", "gender_count")
    .orderBy(desc("gender_count"))
    .show()

  // Задание 2.
  val titlesDF: DataFrame = DatabaseUtils.readTable("public.titles")
  val salariesDF: DataFrame = DatabaseUtils.readTable("public.salaries")

  titlesDF.createOrReplaceTempView("titles")
  salariesDF.createOrReplaceTempView("salaries")

  spark.sql(
      """
        |SELECT t.title, ROUND(AVG(s.salary), 2) as avg_salary
        |FROM titles t
        |JOIN salaries s
        |ON s.emp_no = t.emp_no
        |GROUP BY t.title
        |ORDER BY avg_salary DESC;
        |""".stripMargin)
    //  .show()
    .==(
      titlesDF
        .join(salariesDF, titlesDF.col("emp_no") === salariesDF.col("emp_no"))
        .groupBy(titlesDF.col("title"))
        .avg("salary")
        .orderBy(desc("avg(salary)"))
        .select(col("title"), round(col("avg(salary)"), 2))
        .withColumnRenamed("round(avg(salary), 2)", "avg_salary"))
  //  .show()

}