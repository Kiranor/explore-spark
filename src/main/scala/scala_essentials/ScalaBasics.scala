package scala_essentials

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
  val undeclaredVariable: List[String] = ??? //List.empty[String]
  undeclaredVariable.length

  //  Функции

  def defaultMaxFunction(x: Int, y: Int): Int = {
    if (x > y)
      x
    else
      y
  }

  def printName(name: String): Unit = println(s"Hello, $name!")

  def printGreet(): Unit = println("Greetings")

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
  val combined: List[Int] = intsRange :: intsList

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
  val unknown: Any = 45
  val ordinal = unknown match {
    case String => "first"
    case Int => "second"
    case _ => "unknown"
  }
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

  trait MyTrait {
    def greet(): Unit = println("Greetings!")
  }

  trait MyGenericTrait[A] {
    def ordered(myList: List[A]): Unit
  }

  abstract class MyAbstractClass {
    def abstractDef(x: Int, y: Float): List[Tuple2[Int, Float]]
  }

  case class MyCaseClass(name: String, id: UUID)

  class MyClass(x: Int, y: Float = 10f) extends MyAbstractClass with MyTrait {

    require(y > 10f)

    private val someStaticField = 42

    protected def getSomeField: Int = this.someStaticField

    override def abstractDef(x: Int, y: Float): List[(Int, Float)] = List((x + someStaticField, y + this.getSomeField))

    protected[scala_essentials] def getCaseClass: MyCaseClass = MyCaseClass("someStaticField", UUID.randomUUID())
  }

  object MyClass {
  }

  val t5 = new MyClass(10, 20)

  // Функции высшего порядка map, flatMap, filter - принимают на вход другие функции, которые применяют к элементам последовательности

  import scala.math.pow

  (0 to 10 by 1).map(x => Map(x -> pow(x, 2).toInt))

  val findYear: List[String] = List("2018-03-01", "2019-03-01", "2020-03-01 10:00:00", "Monday 2018-03-01", "2018-03-01 Thursday")
    .filter(_.matches("^2018.*"))

  // Applying flatMap
  val flatMapResult = Seq("Geeks", "for", "Geeks").flatMap(_.toUpperCase)

  // Creating a list of numbers
  val list = List(2, 3, 4)

  // Defining a function
  def myFunction(x:Int) = List(x-1, x, x+1)

  // Applying flatMap
  val result = list.flatMap(myFunction)
}  