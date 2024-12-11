package tasks
import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.{avg, col, count, desc, round}
import org.apache.spark.sql.{DataFrame, RelationalGroupedDataset, SparkSession}
import org.postgresql.Driver
import tasks.JdbcIntegrationTasks.spark

import java.util.Properties

// Создадим объект, содержащий сведения о параметрах подключения к БД, для последующей простоты использования и читаемости кода.
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
  // Для меньшего количества информационных сообщений в логе от spark можно снизить уровень логирования следующей командой.
  spark.sparkContext.setLogLevel("ERROR")

  // Пример чтения из таблицы с помощью метода DatabaseUtils.readTable.
  // При необходимости можете реализовать чтение из таблиц любым удобным способом.
  val employeesDF: DataFrame = DatabaseUtils.readTable("public.employees")
  employeesDF.createTempView("employees")

  // Задание 1. Посчитайте количество работников мужского и женского пола и отсортируйте в порядке убывания.

  // Задание 2. Найдите среднюю зарплату для каждой должности, округлите до 2 знаков после запятой и расположите в порядке убывания.

  // Задание 3. Найдите всех сотрудников, которые работали как минимум в 2-х отделах. Укажите их имя и фамилию в одной колонке, а также количество отделов, в которых они работали. Посчитайте количество таких сотрудников.

  // Задание 4. Найдите имя, фамилию и оклад второго по величине оплачиваемого сотрудника.

  // Задание 5. Для каждого отдела найдите возраст самого молодого сотрудника на дату приёма на работу, самого старого и средний возраст сотрудников при приёме.
}