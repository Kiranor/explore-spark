package scalaEssentials

import java.util.UUID


object ScalaBasics extends App {

  /*  Объявление переменных

  В Scala имеется две разновидности переменных: val-переменные и var-переменные.
  Первые аналогичны финальным переменным в Java или константам в Python. После инициализации val-переменная уже никогда не может быть присвоена повторно (Произойдет ошибка компиляции).
  В отличие от нее var-переменная аналогична нефинальной переменной в Java и может быть присвоена повторно в течение своего жизненного цикла.

  Для любых переменных допустимо не указывать их тип, компилятор автоматически будет выводить их тип на присваиваемого основании выражения справа (логический вывод типов).
  В некоторых случаях все же полезно задавать тип самостоятельно, например, для наследуемых классов, чтобы задать тип родительского класса.
  */

  val valBoolean = true

  //  Ошибка - Reassignment to val
  //  valBoolean = false

  var varBoolean: Boolean = false
  varBoolean = true

  // Символы `???` можно использовать для еще не реализованных методов или переменных.
  // При этом программа не скомпилируется, но будут доступные все подсказки типов для этой переменной. Бывает полезно при прототипирование программы.
  val undeclaredVariable: List[String] = List.empty[String]
  undeclaredVariable.length

  //  Функции

    // Если функция только выполняет какую-то операцию без возвращения значения, то типом ее возвращаемого значения будет Unit
  def printName(name: String): Unit = println(s"Hello, $name!")

    // Функции без переменных

  def printGreet = println("Greetings")

  // Лямбда-функции (анонимные функции)
  //  Анонимная функция, также известная как лямбда, представляет собой блок кода, который передается в качестве аргумента функции высшего порядка.
  //  Википедия определяет анонимную функцию как “определение функции, не привязанное к идентификатору”.

  def defaultMaxFunction(x: Int, y: Int): Int = {
    if (x > y)
      x
    else
      y
  }

  val lambdaMaxFunction: (Int, Int) => Int = (x, y) => if (x > y) x else y

  val t1 = defaultMaxFunction(10, 20)
  val t2 = lambdaMaxFunction(10, 20)

  assert(t2 == t1)

  val x = 1 + 2 == 1.+(2)

  // Управляющие конструкции
  lazy val lazyExpression = if (1 > 2) "Grater" else "Smaller"

  var i = 0
  while (i < args.length) {
    println(args(i))
    i += 1
  }

  for (i <- 0 until 4 if i % 2 != 0) println(i)

  val intsRange: Range = 1 to 12 by 4
  val intsList: List[Int] = (12 to 24 by 5).toList
  val combined: List[Int] = intsRange.toList ::: intsList

  for (i <- intsRange) println(i)

  for {
    i <- intsRange
    j <- intsList
    if j < i
  } {
    println(s"$i, $j")
  }

  for (i <- combined) yield {
    Map(s"Some value of ${i.toString}" -> i)
  }

  for (i <- 1 to 12 by 4; j <- 1 to 15 by 5) println(s"$i, $j")

  (1 to 12 by 4).foreach(println)
  combined.foreach(println)

  // Pattern Matching
  // Сопоставление с примером (Pattern matching) - это механизм сравнения значений с определенным примером.
  // При успешном совпадении значение может быть разложено на составные части. Мы рассматриваем сопоставление с примером, как более мощную версию switch оператора из Java.
  // Eго также можно использовать вместо серии if/else выражений.

  import scala.util.Random

  val randomVal: Int = Random.nextInt(10)

  // Значение константы randomVal выше представляет собой случайное целое число от 0 до 9. randomVal становится левым операндом оператора match, а справа - выражением с четырьмя примерами (называемые еще вариантами).
  // Последний вариант _ - позволяет “поймать все оставшиеся варианты” т.е. для любого числа больше 2.

  val matched = randomVal match {
    case 0 => "zero"
    case 1 => "one"
    case 2 => "two"
    case _ => "other"
  }

  // Как и в Java, в Scala есть конструкция try/catch/finally, позволяющая перехватывать исключения и управлять ими.
  // Для обеспечения согласованности Scala использует тот же синтаксис, что и выражения match, и поддерживает сопоставление с образцом для различных возможных исключений.

  try {
    throw new NullPointerException
  } catch {
    case _: NullPointerException => println("some returned value")
    case _: Throwable => println("something else")
  }
  finally {
    println("finally print something else")
  }

  // Implicits

  // auto-injection by the compiler
  def methodWithImplicitArgument(implicit x: Int) = x + 43

  implicit val implicitInt: Int = 67
  val implicitCall = methodWithImplicitArgument

  //  ООП

  // Traits позволяют указывать (абстрактные) интерфейсы, а также конкретные реализации.
  // В отличие от других языков с поддержкой ООП, таких как Java, возможно, основным инструментом декомпозиции в Scala являются не классы, а трейты.

  trait MyTrait {
    def greet(): Unit = println("Greetings!")
  }

  trait MyGenericTrait[A] {
    def ordered(myList: List[A]): Unit
  }

  abstract class MyAbstractClass {
    def abstractDef(x: Int, y: Float): List[Tuple2[Int, Float]]
  }

  // Case class используются для моделирования неизменяемых структур данных.

  case class MyCaseClass(name: String, id: UUID)

  // Классы могут реализовывать интерфейсы, заданные трейтами и абстрактными классами.

  class MyClass(x: Int, y: Float = 10f) extends MyAbstractClass with MyTrait {

    require(y > 10f)

    private val someStaticField = 42

    protected def getSomeField: Int = this.someStaticField

    override def abstractDef(x: Int, y: Float): List[(Int, Float)] = List((x + someStaticField, y + this.getSomeField))

    protected[scalaEssentials] def getCaseClass: MyCaseClass = MyCaseClass("someStaticField", UUID.randomUUID())
  }

  // Объект — это класс, который имеет ровно один экземпляр. Инициализируется он лениво, тогда, когда на его элементы ссылаются, подобно lazy val.
  // Объекты в Scala позволяют группировать методы и поля в одном пространстве имен, аналогично тому, как вы используете static члены в классе в Java, Javascript (ES6) или @staticmethod в Python.
  object MyClass {
  }

  val t5 = new MyClass(10, 20)

  // Функции высшего порядка map, flatMap, filter - принимают на вход другие функции, которые применяют к элементам последовательности

  import scala.math.pow

  // Функция map принимает на вход другую функцию, которую применять к каждому элементу коллекции
  (0 to 10 by 1).map(x => Map(x -> pow(x, 2).toInt))
  // Функция filter принимает на вход другую функцию (должна возвращать true\false), которую применять к каждому элементу коллекции
  val findYear: List[String] = List("2018-03-01", "2019-03-01", "2020-03-01 10:00:00", "Monday 2018-03-01", "2018-03-01 Thursday")
    .filter(_.matches("^2018.*"))
}  