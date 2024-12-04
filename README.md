## Установка необходимого ПО

- [Установить интегрированную среду разработки IntelliJ IDEA Community Edition](https://www.jetbrains.com/ru-ru/idea/download/?section=windows)
- [Установить плагин Scala](https://plugins.jetbrains.com/plugin/1347-scala)
- [Установить плагин DatabaseHelper](https://plugins.jetbrains.com/plugin/20294-databasehelper)
- [Установить Git](https://git-scm.com/downloads)
- [Установить Java Developers Kit 8 (JDK)](https://bell-sw.com/pages/downloads/#jdk-8-lts)
- [Установить Docker](https://www.docker.com/get-started/)

## Проверка работоспособности

### Проверка установки Java
В командной строке `cmd` выполнить команды:
- `set JAVA_HOME` - проверка корректной установки переменной окружения;
- `java -version` - проверка доступности Java из командной строки.

#### Пример вывода командной строки при корректной установке:

![java_validation.png](files/java_validation.png "Пример вывода командной строки")

### Проверка компиляции и запуска приложений
В текущем проекте перейти в модуль `testpkg`, открыть класс `FirstTestClass` и запустить его выполнение с помощью интерфейса IDEA или комбинацией `Ctrl+Shift+F10`.

Также можно запустить это приложение прямо из текущего файла `README.md`, нажав выше на имя класса со стрелкой. 

Результатом выполнения тестового приложения должен быть вывод в консоль информации, представленной на скриншоте ниже:
![spark_validation.png](files/spark_validation.png "Пример вывода в лог")

## Устранение ошибок

### При запуске spark-shell или кода в IDEA возникает ошибка `org.apache.spark.SparkException: Invalid Spark URL: spark://HeartbeatReceiver@HOME_PC:53176`

В переменные окружения необходимо добавить `SPARK_LOCAL_HOSTNAME = localhost`

## Домашнее задание

Порядок выполнения заданий (от простого к сложному):

1. [`DataFrameTasks.scala`](src/main/scala/tasks/DataFrameTasks.scala)
2. [`DataSourceTasks.scala`](src/main/scala/tasks/DataSourceTasks.scala)
3. [`ColumnsAndExpressionsTasks.scala`](src/main/scala/tasks/ColumnsAndExpressionsTasks.scala)
4. [`AggregationsTasks.scala`](src/main/scala/tasks/AggregationsTasks.scala)
5. [`JoinsTasks.scala`](src/main/scala/tasks/JoinsTasks.scala)
6. [`JdbcIntegrationTasks.scala`](src/main/scala/tasks/JdbcIntegrationTasks.scala)