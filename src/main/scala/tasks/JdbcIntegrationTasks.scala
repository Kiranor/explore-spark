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

  // Задание 5
  // Задание 5.1. Посчитайте количество работников мужского и женского пола и отсортируйте в порядке убывания.

  spark.sql(
    """
      |select gender, count(gender) as gender_count
      |from employees
      |group by gender
      |order by gender_count desc
      |""".stripMargin).show()

  employeesDF
    .groupBy(col("gender"))
    .count()
    .withColumnRenamed("count", "gender_count")
    .orderBy(desc("gender_count")).show()

  // Задание 5.2. Найдите среднюю зарплату для каждой должности, округлите до 2 знаков после запятой и расположите в порядке убывания.
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
    .show()

      titlesDF
        .join(salariesDF, titlesDF.col("emp_no") === salariesDF.col("emp_no"))
        .groupBy(titlesDF.col("title"))
        .avg("salary")
        .orderBy(desc("avg(salary)"))
        .select(col("title"), round(col("avg(salary)"), 2))
        .withColumnRenamed("round(avg(salary), 2)", "avg_salary")
        .show()
  // Задание 5.3. Найдите всех сотрудников, которые работали как минимум в 2-х отделах. Укажите их имя и фамилию в одной колонке, а также количество отделов, в которых они работали. Посчитайте количество таких сотрудников.
  DatabaseUtils.readTable("public.employees").createOrReplaceTempView("employees")
  DatabaseUtils.readTable("public.dept_emp").createOrReplaceTempView("dept_emp")

  spark.sql(
    """
      |SELECT CONCAT(any_value(e.first_name), ' ' , any_value(e.last_name)) AS name, count(d.dept_no) as number_of_departments
      |FROM employees e
      |JOIN dept_emp d
      |ON e.emp_no = d.emp_no
      |GROUP BY e.emp_no
      |HAVING COUNT(d.dept_no) > 1
      |order by name asc
      |""".stripMargin)
    .show()

  // Задание 5.4. Найдите имя, фамилию и оклад второго по величине оплачиваемого сотрудника.
  spark.sql(
    """
      |SELECT CONCAT(employees.first_name, ' ', employees.last_name) AS employee_name, salaries.salary
      |FROM employees JOIN salaries ON employees.emp_no = salaries.emp_no
      |WHERE salaries.salary = (SELECT MAX(salaries.salary) FROM salaries)
      |""".stripMargin).show()

  // Задание 5.5. Для каждого отдела найдите возраст самого молодого сотрудника на дату приёма на работу, самого старого и средний возраст сотрудников при приёме.
  DatabaseUtils.readTable("public.employees").createOrReplaceTempView("employees")
  DatabaseUtils.readTable("public.dept_emp").createOrReplaceTempView("dept_emp")
  DatabaseUtils.readTable("public.departments").createOrReplaceTempView("departments")

  spark.sql(
    """
      |SELECT dept.dept_name,
      |       MAX(TIMESTAMPDIFF(YEAR, e.birth_date, e.hire_date)) AS max_age_hire_date,
      |       MIN(TIMESTAMPDIFF(YEAR, e.birth_date, e.hire_date)) AS min_age_hire_date,
      |       AVG(TIMESTAMPDIFF(YEAR, e.birth_date, e.hire_date)) AS avg_age_hire_date
      |FROM employees e
      | JOIN dept_emp d_emp ON e.emp_no = d_emp.emp_no
      | JOIN departments dept ON d_emp.dept_no = dept.dept_no
      |GROUP BY dept.dept_name
      |""".stripMargin).show(false)
}