# [Scala 简介](https://www.baeldung.com/scala/scala-intro)

1. 简介

    在本教程中，我们将学习在 Java 虚拟机上运行的主要语言之一 [Scala](https://www.scala-lang.org/)。

    我们将从值、变量、方法和控制结构等核心语言特性开始。然后，我们将探索一些高级功能，如高阶函数、卷曲、类、对象和模式匹配。

    要了解 JVM 语言的概况，请查看我们的 [JVM 语言快速指南](https://www.baeldung.com/jvm-languages)。

2. 项目设置

    在本教程中，我们将使用 <https://www.scala-lang.org/download/> 上的标准 Scala 安装程序。

    首先，在 pom.xml 中添加 scala-library 依赖关系。该工件提供了该语言的标准库：

    ```xml
    <dependency>
        <groupId>org.scala-lang</groupId>
        <artifactId>scala-library</artifactId>
        <version>2.13.10</version>
    </dependency>
    ```

    其次，添加 scala-maven-plugin 用于编译、测试、运行和记录代码：

    ```xml
    <plugin>
        <groupId>net.alchim31.maven</groupId>
        <artifactId>scala-maven-plugin</artifactId>
        <version>3.3.2</version>
        <executions>
            <execution>
                <goals>
                    <goal>compile</goal>
                    <goal>testCompile</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    ```

    Maven 有最新的 scala-lang 和 scala-maven-plugin 工件。

    最后，我们将使用 JUnit 进行单元测试。

3. 基本功能

    在本节中，我们将通过示例考察语言的基本特性。为此，我们将使用 [Scala 解释器](https://docs.scala-lang.org/overviews/repl/overview.html)。

    1. 解释器

        解释器是一个用于编写程序和表达式的交互式外壳。

        让我们用它来打印 “hello world”：

        ```command
        C:\>scala
        Welcome to Scala 2.13.10 (Java HotSpot(TM)
        64-Bit Server VM, Java 1.8.0_92).
        Type in expressions for evaluation. 
        Or try :help.

        scala> print("Hello World!")
        Hello World!
        scala>
        ```

        上面，我们在命令行输入 “scala” 来启动解释器。解释器启动后会显示一条欢迎信息和一个提示。

        然后，我们在提示符下输入表达式。解释器会读取表达式，对其进行求值并打印结果。然后，解释器循环运行并再次显示提示。

        由于解释器能提供即时反馈，因此是入门该语言的最简单方法。因此，让我们用它来探索语言的基本特征：表达式和各种定义。

    2. 表达式

        任何可计算的语句都是表达式。

        让我们写一些表达式并看看它们的结果：

        ```scala
        scala> 123 + 321
        res0: Int = 444

        scala> 7 * 6
        res1: Int = 42

        scala> "Hello, " + "World"
        res2: String = Hello, World

        scala> "zipZAP" * 3
        res3: String = zipZAPzipZAPzipZAP

        scala> if (11 % 2 == 0) "even" else "odd"
        res4: String = odd
        ```

        如上所示，每个表达式都有一个值和一个类型。

        如果表达式没有任何返回值，它将返回 Unit 类型的值。这种类型只有一个值：（）。它类似于 Java 中的 void 关键字。

    3. 值定义

        关键字 val 用于声明值。

        我们用它来命名表达式的结果：

        ```scala
        scala> val pi:Double = 3.14
        pi: Double = 3.14

        scala> print(pi)
        3.14
        ```

        这样做可以多次重复使用结果。

        值是不可变的。因此，我们不能重新赋值：

        ```scala
        scala> pi = 3.1415
        <console>:12: error: reassignment to val
                pi = 3.1415
                ^
        ```

    4. 变量定义

        如果需要重新赋值，我们可以将其声明为变量。

        关键字 var 用于声明变量：

        ```scala
        scala> var radius:Int=3
        radius: Int = 3
        ```

    5. 方法定义

        我们使用 def 关键字定义方法。在关键字之后，我们指定方法名称、参数声明、分隔符（冒号）和返回类型。之后，我们指定一个分隔符（=），接着是方法体。

        与 Java 不同，我们不使用 return 关键字来返回结果。方法返回的是最后求值的表达式的值。

        让我们编写一个 avg 方法来计算两个数字的平均值：

        ```scala
        scala> def avg(x:Double, y:Double):Double = {
            (x + y) / 2
        }
        avg: (x: Double, y: Double)Double
        ```

        然后，让我们调用这个方法：

        ```scala
        scala> avg(10,20)
        res0: Double = 12.5
        ```

        如果一个方法不带任何参数，我们可以在定义和调用时省略括号。此外，如果主体只有一个表达式，我们也可以省略大括号。

        让我们编写一个无参数方法 coinToss，随机返回 “Head” 或 “Tail”：

        ```scala
        scala> def coinToss =  if (Math.random > 0.5) "Head" else "Tail"
        coinToss: String
        ```

        接下来，让我们调用这个方法：

        ```scala
        scala> println(coinToss)
        Tail
        scala> println(coinToss)
        Head
        ```

4. 控制结构

    控制结构允许我们改变程序中的控制流。我们有以下控制结构：

    - If-else 表达式
    - While 循环和 do while 循环
    - For 表达式
    - Try 表达式
    - Match 表达式

    与 Java 不同，我们没有 continue 或 break 关键字。我们有 return 关键字。不过，我们应避免使用它。

    与 switch 语句不同，我们可以通过匹配表达式进行模式匹配。此外，我们还可以定义自己的控制抽象。

    1. if-else

        if-else 表达式与 Java 类似。else 部分是可选的。我们可以嵌套多个 if-else 表达式。

        由于它是一个表达式，因此会返回一个值。因此，我们使用它类似于 Java 中的三元运算符（? 事实上，Java 语言中并没有三元运算符。

        让我们使用 if-else 写一个计算最大公约数的方法：

        ```scala
        def gcd(x: Int, y: Int): Int = {
        if (y == 0) x else gcd(y, x % y)
        }
        ```

        然后，让我们为这个方法写一个单元测试：

        ```scala
        @Test
        def whenGcdCalledWith15and27_then3 = {
        assertEquals(3, gcd(15, 27))
        }
        ```

    2. while 循环

        while 循环有一个条件和一个主体。当条件为真时，它会在循环中反复求值主体--条件在每次迭代开始时求值。

        由于没有有用的信息返回，所以它返回 Unit。

        让我们使用 while 循环编写一个计算最大公约数的方法：

        ```scala
        def gcdIter(x: Int, y: Int): Int = {
            var a = x
            var b = y
            while (b > 0) {
                a = a % b
                val t = a
                a = b
                b = t
            }
            a
        }
        ```

        然后，让我们验证一下结果：

        `assertEquals(3, gcdIter(15, 27))`

    3. do while 循环

        do while 循环与 while 循环类似，只是循环条件在循环结束时进行评估。

        让我们使用 do while 循环编写一个计算阶乘的方法：

        ```scala
        def factorial(a: Int): Int = {
            var result = 1
            var i = 1
            do {
                result *= i
                i = i + 1
            } while (i <= a) {
                result
            }
        }
        ```

        接下来，我们来验证结果：

        `assertEquals(720, factorial(6))`

    4. for 表达式

        for 表达式比 Java 中的 for 循环用途更广。

        它可以遍历单个或多个集合。此外，它还可以筛选出元素并生成新的集合。

        让我们使用 for 表达式编写一个方法，对一定范围的整数求和：

        ```scala
        def rangeSum(a: Int, b: Int) = {
            var sum = 0
                for (i <- a to b) {
                    sum += i
                }
            sum
        }
        ```

        这里，a to b 是一个生成器表达式。它生成一系列从 a 到 b 的值。i <- a to b 是一个生成器表达式。它将 i 定义为 val，并将生成器表达式产生的一系列值赋值给它。

        正文将针对系列中的每个值执行。

        接下来，我们来验证结果：`assertEquals(55, rangeSum(1, 10))`

5. 函数

    Scala 是一种函数式语言。函数在这里是一等值--我们可以像使用其他值类型一样使用它们。在本节中，我们将学习一些与函数相关的高级概念--局部函数、高阶函数、匿名函数和卷曲。

    1. 局部函数

        我们可以在函数内部定义函数。它们被称为嵌套函数或局部函数。与局部变量类似，它们只在其定义的函数中可见。

        现在，让我们使用嵌套函数编写一个计算 power 的方法：

        ```scala
        def power(x: Int, y:Int): Int = {
            def powNested(i: Int,
                            accumulator: Int): Int = {
                if (i <= 0) accumulator
                else powNested(i - 1, x * accumulator)
            }
            powNested(y, 1)
        }
        ```

        接下来，让我们验证一下结果：

        `assertEquals(8, power(2, 3))`

        5.2. 高阶函数

        由于函数是值，我们可以将它们作为参数传递给另一个函数。我们还可以让函数返回另一个函数。

        我们将对函数进行操作的函数称为高阶函数。它们能让我们在更抽象的层次上工作。使用它们，我们可以通过编写通用算法来减少代码的重复。

        现在，让我们编写一个高阶函数，对整数范围执行 map 和 reduce 操作：

        ```scala
        def mapReduce(r: (Int, Int) => Int,
                    i: Int,
                    m: Int => Int,
                    a: Int, b: Int) = {
            def iter(a: Int, result: Int): Int = {
                if (a > b) {
                    result
                } else {
                    iter(a + 1, r(m(a), result))
                }
            }
            iter(a, i)
        }
        ```

        这里，r 和 m 是函数类型的参数。通过传递不同的函数，我们可以解决一系列问题，例如平方和、立方和阶乘。

        接下来，让我们用这个函数再写一个求整数平方和的函数 sumSquares：

        ```scala
        @Test
        def whenCalledWithSumAndSquare_thenCorrectValue = {
            def square(x: Int) = x * x
            def sum(x: Int, y: Int) = x + y

            def sumSquares(a: Int, b: Int) =
                mapReduce(sum, 0, square, a, b)

            assertEquals(385, sumSquares(1, 10))
        }
        ```

        从上面我们可以看出，高阶函数往往会创建许多小型单用函数。我们可以使用匿名函数来避免命名这些函数。

        5.3. 匿名函数

        匿名函数是一个求值为函数的表达式。它类似于 Java 中的 lambda 表达式。

        让我们使用匿名函数重写前面的示例：

        ```scala
        @Test
        def whenCalledWithAnonymousFunctions_thenCorrectValue = {
            def sumSquares(a: Int, b: Int) =
                mapReduce((x, y) => x + y, 0, x => x * x, a, b)
            assertEquals(385, sumSquares(1, 10))
        }
        ```

        在本例中，mapReduce 接收到两个匿名函数：(x, y) => x + y 和 x => x * x。

        Scala 可以根据上下文推导出参数类型。因此，我们省略了这些函数中的参数类型。

        这样，代码就比之前的示例更简洁了。

        5.4. 卷曲函数

        咖喱函数包含多个参数列表，例如 def f(x: Int) (y: Int)。它通过传递多个参数列表来应用，如 f(5)(6)。

        该函数以调用一连串函数的方式进行求值。这些中间函数接受一个参数并返回一个函数。

        我们也可以部分指定参数列表，如 f(5)。

        现在，让我们通过一个例子来理解这一点：

        ```scala
        @Test
        def whenSumModCalledWith6And10_then10 = {
            // a curried function
            def sum(f : Int => Int)(a : Int, b : Int) : Int =
                if (a > b) 0 else f(a) + sum(f)(a + 1, b)

            // another curried function
            def mod(n : Int)(x : Int) = x % n

            // application of a curried function
            assertEquals(1, mod(5)(6))
            
            // partial application of curried function
            // trailing underscore is required to 
            // make function type explicit
            val sumMod5 = sum(mod(5)) _

            assertEquals(10, sumMod5(6, 10))
        }
        ```

        上面，sum 和 mod 各包含两个参数列表。
        我们传递两个参数列表，如 mod(5)(6)。这将以两次函数调用的形式进行运算。首先，对 mod(5) 进行求值，返回一个函数。然后，再调用参数 6。结果是 1。

        在 mod(5) 中可以部分应用参数。结果是一个函数。

        同样，在表达式 sum(mod(5)) _ 中，我们只向 sum 函数传递了第一个参数。因此，sumMod5 是一个函数。

        下划线用作未应用参数的占位符。由于编译器无法推断出函数类型，因此我们使用尾部的下划线来明确函数的返回类型。

        5.5. 同名参数

        函数可以通过两种不同的方式应用参数--按值参数和按名参数。与此相反，函数在引用同名参数时都会对其进行评估。如果未使用同名参数，则不会对其进行评估。

        Scala 默认使用旁值参数。如果参数类型前面有箭头 ( =>)，它就会切换到旁名参数。

        现在，让我们用它来实现 while 循环：

        ```scala
        def whileLoop(condition: => Boolean)(body: => Unit): Unit =
        if (condition) {
            body
            whileLoop(condition)(body)
        }
        ```

        要使上述函数正常工作，每次引用时都要对参数 condition 和 body 进行评估。因此，我们将它们定义为同名参数。

6. 类定义

    我们用 class 关键字定义一个类，后面跟类名。

    在名称后，我们可以指定主构造函数参数。这样做会自动为类添加同名成员。

    在类主体中，我们定义成员--值、变量、方法等。除非使用 private 或 protected 访问修饰符进行修改，否则它们默认为公共成员。

    我们必须使用覆盖关键字来覆盖超类中的方法。

    让我们定义一个 Employee 类：

    ```scala
    class Employee(val name : String, var salary : Int, annualIncrement : Int = 20) {
    def incrementSalary() : Unit = {
        salary += annualIncrement
    }

    override def toString = 
        s"Employee(name=$name, salary=$salary)"
    }
    ```

    在这里，我们指定了三个构造函数参数：name、salary 和 annualIncrement。由于我们使用 val 和 var 关键字声明 name 和 salary，因此相应的成员是公共的。另一方面，我们没有为 annualIncrement 参数使用 val 或 var 关键字。因此，相应的成员是私有的。由于我们为该参数指定了默认值，因此在调用构造函数时可以省略它。

    除了字段外，我们还定义了方法 incrementSalary。该方法是公共的。

    接下来，让我们为该类编写一个单元测试：

    ```scala
    @Test
    def whenSalaryIncremented_thenCorrectSalary = {
    val employee = new Employee("John Doe", 1000)
    employee.incrementSalary()
    assertEquals(1020, employee.salary)
    }
    ```

    1. 抽象类

        我们使用关键字 abstract 来抽象一个类。它类似于 Java 中的抽象类。它可以拥有普通类可以拥有的所有成员。

        此外，它还可以包含抽象成员。这些成员只有声明而没有定义，其定义在子类中提供。

        与 Java 类似，我们不能创建抽象类的实例。现在，让我们用一个例子来说明抽象类。

        首先，我们创建一个抽象类 IntSet 来表示整数集合：

        ```scala
        abstract class IntSet {
            // add an element to the set
            def incl(x: Int): IntSet

            // whether an element belongs to the set
            def contains(x: Int): Boolean
        }
        ```

        接下来，让我们创建一个具体的子类 EmptyIntSet 来表示空集：

        ```scal
        class EmptyIntSet extends IntSet {
            def contains(x : Int) = false
            def incl(x : Int) =
            new NonEmptyIntSet(x, this)
        }
        ```

        然后，另一个子类 NonEmptyIntSet 表示非空集：

        ```scala
        class NonEmptyIntSet(val head : Int, val tail : IntSet)
        extends IntSet {

        def contains(x : Int) =
            head == x || (tail contains x)
        def incl(x : Int) =
            if (this contains x) {
                this
            } else {
                new NonEmptyIntSet(x, this)
            }
        }
        ```

        最后，让我们为 NonEmptySet 写一个单元测试：

        ```scala
        @Test
        def givenSetOf1To10_whenContains11Called_thenFalse = {
            // Set up a set containing integers 1 to 10.
            val set1To10 = Range(1, 10)
                .foldLeft(new EmptyIntSet() : IntSet) {
                    (x, y) => x incl y
                }
            assertFalse(set1To10 contains 11)
        }
        ```

    2. 特质

        特质与 Java 接口相对应，但有以下区别：

        - 能从类扩展
        - 可以访问超类成员
        - 可以有初始化语句

        我们使用 trait 关键字定义它们，就像定义类一样。此外，除了构造函数参数外，它们可以拥有与抽象类相同的成员。此外，它们还可以作为混合类添加到其他类中。

        现在，让我们用一个例子来说明特质。

        首先，让我们定义一个 trait UpperCasePrinter，以确保 toString 方法返回的值是大写字母：

        ```scala
        trait UpperCasePrinter {
            override def toString =
                super.toString.toUpperCase
        }
        ```

        然后，让我们将该特质添加到 Employee 类中进行测试：

        ```scala
        @Test
        def givenEmployeeWithTrait_whenToStringCalled_thenUpper = {
            val employee = new Employee("John Doe", 10) with UpperCasePrinter
            assertEquals("EMPLOYEE(NAME=JOHN DOE, SALARY=10)", employee.toString)
        }
        ```

        类、对象和特质最多只能继承一个类，但可以继承任意多个特质。

7. 对象定义

    对象是类的实例。正如我们在前面的示例中看到的，我们使用 new 关键字从类中创建对象。

    但是，如果一个类只能有一个实例，我们就需要防止创建多个实例。在 Java 中，我们使用单例模式来实现这一点。

    对于这种情况，我们有一种简洁的语法，叫做对象定义--与类定义类似，但有一点不同。我们不使用类关键字，而是使用对象关键字。这样做可以定义一个类，并轻松创建其唯一实例。

    我们使用对象定义来实现实用方法和单例。

    让我们定义一个 Utils 对象：

    ```scala
    object Utils {
    def average(x: Double, y: Double) =
        (x + y) / 2
    }
    ```

    在这里，我们定义了 Utils 类，并创建了它的唯一实例。

    我们使用 Utils 这个名称来称呼这个唯一的实例。该实例在首次访问时创建。

    我们不能使用 new 关键字创建 Utils 的另一个实例。

    现在，让我们为 Utils 对象编写一个单元测试：

    `assertEquals(15.0, Utils.average(10, 20), 1e-5)`

    1. 同伴对象和同伴类

        如果一个类和一个对象的定义具有相同的名称，我们就把它们分别称为伴生类和伴生对象。我们需要在同一个文件中定义两者。同伴对象可以访问同伴类的私有成员，反之亦然。

        与 Java 不同，我们没有静态成员。相反，我们使用同伴对象来实现静态成员。

8. 模式匹配

    模式匹配是将一个表达式与一连串备选表达式进行匹配。每种模式都以关键字 case 开始。随后是模式、分隔符箭头（=>）和若干表达式。如果模式匹配，则对表达式进行评估。

    我们可以通过以下方式构建模式

    - case 类构造函数
    - 变量模式
    - 通配符模式 _
    - 文字
    - 常量标识符

    大小写类可以方便地对对象进行模式匹配。我们在定义类时添加 case 关键字，使其成为 case 类。因此，模式匹配比 Java 中的 switch 语句强大得多。因此，它是一种被广泛使用的语言特性。

    现在，让我们使用模式匹配来编写斐波那契方法：

    ```scala
    def fibonacci(n:Int) : Int = n match {
    case 0 | 1 => 1
    case x if x > 1 =>
        fibonacci (x-1) + fibonacci(x-2)
    }
    ```

    接下来，让我们为这个方法编写一个单元测试：

    `assertEquals(13, fibonacci(6))`

9. sbt 使用

    [sbt](https://www.baeldung.com/scala/sbt-intro) 是 Scala 项目事实上的构建工具。在本节中，我们将了解它的基本用法和关键概念。

    1. 设置项目

        我们可以使用 sbt 设置 Scala 项目并管理其生命周期。[下载](https://www.scala-sbt.org/download.html)并安装后，让我们来建立一个简约的 Scala 项目 scala-demo。

        首先，我们必须在 build.sbt 文件中添加项目设置，如名称、组织、版本和 scalaVersion：

        ```scala
        $ cat build.sbt
        scalaVersion := "3.3.0"
        version := "1.0"
        name := "sbt-demo"
        organization := "com.baeldung"
        ```

        现在，当我们从项目根目录运行 sbt 命令时，它将启动 [sbt shell](https://www.scala-sbt.org/1.x/docs/Running.html)：

        ```shell
        scala-demo $ sbt
        [info] Updated file /Users/tavasthi/baeldung/scala-demo/project/build.properties: set sbt.version to 1.8.3
        [info] welcome to sbt 1.8.3 (Homebrew Java 20.0.1)
        [info] loading global plugins from /Users/tavasthi/.sbt/1.0/plugins
        [info] loading project definition from /Users/tavasthi/baeldung/scala-demo/project
        [info] loading settings for project scala-demo from build.sbt ...
        [info] set current project to sbt-demo (in build file:/Users/tavasthi/baeldung/scala-demo/)
        [info] sbt server started at local:///Users/tavasthi/.sbt/1.0/server/de978fbf3c48749b6213/sock
        [info] started sbt server
        sbt:sbt-demo>
        ```

        此外，它会创建或更新管理项目生命周期所需的 project/ 和 target/ 目录：

        ```shell
        $ ls -ll
        total 8
        -rw-r--r--  1 tavasthi  staff   94 Jul 22 22:40 build.sbt
        drwxr-xr-x  4 tavasthi  staff  128 Jul 22 22:40 project
        drwxr-xr-x  5 tavasthi  staff  160 Jul 22 22:41 target
        ```

        最后，默认情况下，sbt 采用传统的项目结构，src/main 和 src/test 目录分别包含主源代码和测试源代码。因此，让我们使用这种约定来设置项目结构：

        `$ mkdir -p src/{main,test}/scala/com/baeldung`

        我们必须注意，我们创建的目录层次结构是根据 build.sbt 文件中使用的组织名称来保存文件的。

    2. 编译代码

        使用 sbt shell，我们可以方便地构建 Scala 项目。

        首先，在源代码中添加一个 Main.scala 文件，用于打印 “Hello, world!”文本：

        ```shell
        $ cat src/main/scala/com/baeldung/Main.scala
        package com.baeldung
        object Main {
            val helloWorldStr = "Hello, world!"
            def main(args: Array[String]): Unit = println(helloWorldStr)
        }
        ```

        同样，让我们在 src/test/scala 目录下添加 DemoTest.scala 测试文件：

        ```shell
        $ cat src/test/scala/com/baeldung/DemoTest.scala
        package com.baeldung
        import org.scalatest.funsuite.AnyFunSuite
        class DemoTest extends AnyFunSuite {
            test("add two numbers") {
                assert(2 + 2 == 4)
            }
        }
        ```

        现在，我们可以使用 sbt 编译命令编译代码了：

        ```shell
        $ sbt compile
        [info] welcome to sbt 1.8.3 (Homebrew Java 20.0.1)
        [info] loading global plugins from /Users/tavasthi/.sbt/1.0/plugins
        [info] loading project definition from /Users/tavasthi/baeldung/scala-demo/project
        [info] loading settings for project scala-demo from build.sbt ...
        [info] set current project to sbt-demo (in build file:/Users/tavasthi/baeldung/scala-demo/)
        [info] Executing in batch mode. For better performance use sbt's shell
        [success] Total time: 0 s, completed Jul 23, 2023, 12:53:39 PM
        ```

        太好了 看来我们做对了。

    3. 依赖关系管理

        我们还可以使用 sbt 工具管理项目中的依赖关系。要添加软件包依赖关系，我们需要更新 build.sbt 文件中的 libraryDependencies 属性。

        针对我们的用例，让我们添加对 scalatest 软件包的依赖，运行测试代码时将需要该软件包：

        ```scala
        libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest" % "3.2.13" % Test
        )
        ```

        我们必须注意，我们使用了 ++= 操作符来扩展配置，并将 % 作为组织、工件和版本信息之间的分隔符。此外，我们还使用了 %% 操作符注入项目的 Scala 版本，并使用 Test 关键字定义了依赖关系的范围。

    4. 运行主代码和测试代码

        我们可以使用 sbt 工具来运行项目中的主代码和测试代码。

        首先，使用 sbt run 命令运行主代码：

        ```shell
        $ sbt run
        [info] welcome to sbt 1.8.3 (Homebrew Java 20.0.1)
        [info] loading global plugins from /Users/tavasthi/.sbt/1.0/plugins
        [info] loading project definition from /Users/tavasthi/baeldung/scala-demo/project
        [info] loading settings for project scala-demo from build.sbt ...
        [info] set current project to sbt-demo (in build file:/Users/tavasthi/baeldung/scala-demo/)
        [info] running com.baeldung.Main
        Hello, world!
        [success] Total time: 0 s, completed Jul 23, 2023, 1:01:09 PM
        ```

        完美！我们可以看到主代码运行成功。

        现在，让我们使用 sbt test 命令运行项目测试：

        ```shell
        $ sbt test
        [info] welcome to sbt 1.8.3 (Homebrew Java 20.0.1)
        [info] loading global plugins from /Users/tavasthi/.sbt/1.0/plugins
        [info] loading project definition from /Users/tavasthi/baeldung/scala-demo/project
        [info] loading settings for project scala-demo from build.sbt ...
        [info] set current project to sbt-demo (in build file:/Users/tavasthi/baeldung/scala-demo/)
        [info] compiling 1 Scala source to /Users/tavasthi/baeldung/scala-demo/target/scala-3.3.0/test-classes ...
        [info] DemoTest:
        [info] - add two numbers
        [info] Run completed in 136 milliseconds.
        [info] Total number of tests run: 1
        [info] Suites: completed 1, aborted 0
        [info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
        [info] All tests passed.
        [success] Total time: 2 s, completed Jul 23, 2023, 1:03:52 PM
        ```

        完成我们可以看到测试运行正常。

10. 结论

    在本文中，我们了解了 Scala 语言并学习了它的一些关键特性。正如我们所看到的，它为命令式、函数式和面向对象编程提供了出色的支持。
