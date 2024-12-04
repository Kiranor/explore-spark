# Установка необходимого ПО

- [Установить интегрированную среду разработки IntelliJ IDEA Community Edition](https://www.jetbrains.com/ru-ru/idea/download/?section=windows)
- [Установить плагин Scala](https://plugins.jetbrains.com/plugin/1347-scala)
- [Установить плагин DatabaseHelper](https://plugins.jetbrains.com/plugin/20294-databasehelper)
- [Установить Git](https://git-scm.com/downloads)
- [Установить Java Developers Kit 8 (JDK)](https://bell-sw.com/pages/downloads/#jdk-8-lts)
- [Установить Docker](https://www.docker.com/get-started/)

# Клонирование репозитория
Для начала работы с репозиторием после того как было установлено все необходимое ПО следует:
- Запустить IntelliJ IDEA Community Edition;
- Клонировать репозиторий.

![clone_repo.png](files/clone_repo.png)
![clone_idea_1.png](files/clone_idea_1.png)
![clone_idea_2.png](files/clone_idea_2.png)

# Проверка работоспособности

## Проверка установки Java
В командной строке `cmd` выполнить команды:
- `set JAVA_HOME` - проверка корректной установки переменной окружения;
- `java -version` - проверка доступности Java из командной строки.

### Пример вывода командной строки при корректной установке:

![java_validation.png](files/java_validation.png "Пример вывода командной строки")

## Проверка компиляции и запуска приложений
В текущем проекте перейти в модуль `testpkg`, открыть класс `FirstTestClass` и запустить его выполнение с помощью интерфейса IDEA или комбинацией `Ctrl+Shift+F10`.

Также можно запустить это приложение прямо из текущего файла `README.md`, нажав выше на имя класса со стрелкой. 

Результатом выполнения тестового приложения должен быть вывод в консоль информации, представленной на скриншоте ниже:
![spark_validation.png](files/spark_validation.png "Пример вывода в лог")

# Устранение ошибок

## При запуске spark-shell или кода в IDEA возникает ошибка `org.apache.spark.SparkException: Invalid Spark URL: spark://HeartbeatReceiver@HOME_PC:53176`

В переменные окружения необходимо добавить `SPARK_LOCAL_HOSTNAME = localhost`

# Домашнее задание

Порядок выполнения заданий (от простого к сложному):

## 1. [`DataFrameTasks.scala`](src/main/scala/tasks/DataFrameTasks.scala)
## 2. [`DataSourceTasks.scala`](src/main/scala/tasks/DataSourceTasks.scala)
## 3. [`ColumnsAndExpressionsTasks.scala`](src/main/scala/tasks/ColumnsAndExpressionsTasks.scala)
## 4. [`AggregationsTasks.scala`](src/main/scala/tasks/AggregationsTasks.scala)
## 5. [`JoinsTasks.scala`](src/main/scala/tasks/JoinsTasks.scala)
## 6. [`JdbcIntegrationTasks.scala`](src/main/scala/tasks/JdbcIntegrationTasks.scala)

В рамках данного задания предполагается интеграционное взаимодействие с сервером БД Postgres, развернутого в локальном Docker-контейнере.
Конфигурация образа находится в файле [docker-compose.yml](docker-compose.yml) и включает название контейнера, порт для подключения, а также имя пользователя и пароль для подключения.

Для запуска сервера БД в контейнере необходимо находясь в корневой директории текущего проекта в командной строке выполнить команду `docker-compose up` и дождаться завершения скачивания и развёртывания контейнера.

Для проверки успешности запуска контейнера с помощью команды `docker ps` необходимо вывести список запущенных контейнеров.
Пример запущенного контейнера БД Postgres представлен на следующем скриншоте.
![docker_ps.png](files/docker_ps.png)

Для проверки успешности создания объектов БД с помощью плагина `DatabaseHelper` необходимо подключиться к БД и посмотреть на созданные объекты.
Host - localhost, database - explore_spark, user - docker, password - docker.

Шаги для подключения представлены на скриншотах ниже.
![database_viewer.png](files/database_viewer.png)
![pg_connection_properties.png](files/pg_connection_properties.png)

В итоге в БД должны быть присутствовать следующие таблицы, представленные на скриншоте.

![pg_connection_tables.png](files/pg_connection_tables.png)

После завершения работы по заданию остановить контейнер можно с помощью команды `docker stop postgres` и убедиться, что больше нет запущенных контейнеров с помощью команды `docker ps` как показано на скриншоте ниже.
![docker_stop.png](files/docker_stop.png)