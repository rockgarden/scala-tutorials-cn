# [什么是 Apache Spark RDD？](https://www.baeldung.com/scala/apache-spark-rdd)

1. 概述

    随着我们之间的联系越来越紧密，我们的数字化生存越来越个性化，再加上全球化和通过互联网消除物理距离，我们处理和创建的数据量与日俱增。

    为了处理这些数据量，我们采用了一些技巧和技术，例如 MapReduce 编程模型、计算机集群并行性和 [Apache Spark](https://www.baeldung.com/apache-spark) 框架，后者提供了处理数据的分布式数据系统。

    在本教程中，我们将了解 Spark 的一种基本数据结构--弹性分布式数据集（RDD）。

2. Spark RDD

    RDD 是分布在集群中所有节点上的记录集合的不可变、弹性和分布式表示。

    在 Spark 编程中，RDD 是最基本的数据结构。[数据集和数据帧](https://www.baeldung.com/java-spark-dataframe-dataset-rdd)建立在 RDD 的基础上。

    Spark RDD 通过应用程序接口（API）呈现，其中数据集被表示为一个对象，通过方法，我们可以对其应用逻辑。我们通过这个 API 来定义 Spark 如何执行和进行所有转换。

    此外，有了这个低级应用程序接口，我们就能实现类型安全，并灵活地操作数据。

    1. Spark 架构

        Apache Spark 的设计旨在利用机器集群的最佳性能。该架构由三个主要部分组成：驱动程序、集群管理器和工作者。

        驱动程序负责协调 Spark 应用程序，集群管理器负责管理所有使用的资源和工作者，最后，工作者负责执行程序的任务。

    2. 数据结构

        RDD 数据结构基于控制和优化代码的原则和特性。其中包括

        - 不变性 Immutability： 这是函数式编程的一个重要概念，它的好处是使并行变得更容易。每当我们要改变 RDD 的状态时，我们都要创建一个新的 RDD，并完成所有转换。
        - 内存计算 In-memory computation： 利用 Spark，我们可以处理 RAM 中的数据，而不是磁盘中的数据。因为与磁盘相比，使用 RAM 可以提高加载和处理性能。
        - 容错性 Fault-Tolerant： 使用这种架构，即使最终出现故障，系统也能保持正常工作。
        - 分区 Partitioning： 将 RDDs 数据分布到各个节点，以便更好地进行计算。
        - 懒评估 Lazy evaluation： 除性能外，Spark RDD 还采用了懒散评估，只处理必要的数据，并在其后进行优化（数据帧和数据集的查询计划由 Catalyst 优化）。

        RDD 是 Apache Spark 的第一个结构。此外，如今的其他结构在某些情况下已被证明更加高效。不过，RDD 并没有被弃用，而是被普遍使用。

        如前所述，DataFrames（数据帧）和 Datasets（数据集）都是在 RDD 的基础上构建的，因此它仍然是 Spark 的核心。

        此外，在谈及代码优化时，DataFrames 和 Datasets 也是很好的选择，这得益于 Catalyst、Tungsten 的空间效率以及结构化数据。此外，高端 API 更易于编码和理解。

        但是，RDDs 仍然是一个不错的选择，它可以通过底层 API 灵活控制数据集，无需 DSL（领域专用语言）即可进行数据操作。

        尤其是在处理非结构化数据且性能并不重要的情况下。

    3. 创建 RDD

        考虑到所有这些理论信息，我们来创建一个 RDD。

        有两种方法： 并行化集合和从源文件读取数据。

        让我们看看如何通过并行化集合创建 RDD：

        ```scala
        val animals = List("dog", "cat", "frog", "horse")
        val animalsRDD = sc.parallelize(animals)
        ```

        在上面的示例中，我们得到了 animalsRDD：RDD。

        第二种方法是从某个地方加载数据。让我们来看看：

        ```scala
        val data = sc.textFile("data.txt")
        // rdd: RDD[String] = data.txt MapPartitionsRDD[1] at textFile...
        ```

        此外，我们还可以始终以 DataFrame/Dataset 的形式读取数据，然后使用 .rdd 方法将其转换为 RDD：

        ```scala
        val dataDF = spark.read.csv("data.csv")
        val rdd = df.rdd
        rdd: RDD
        ```

3. 操作

    函数负责 RDD 中的所有操作。这些函数包括数据操作、持久化、交互、加载。例如：map()、filter()、save()。

    Spark 函数分为两类：转换（Transformations）和操作（Actions）。

    1. 转换

        转换操作是将给定的 RDD 转换为返回值的函数。这些函数是懒惰的。换句话说，只有当操作发生时，它们才会被执行。

        换句话说，只有在必要时，才会执行对 RDD 进行任何更改的函数。例如，map()、join()、sort()、filter() 都是转换函数。

        也就是说，由于这种 “懒惰 laziness”，我们不会不必要地跨 Worker 发送驱动程序。举个例子： 如果我们对 RDD 进行映射和还原，对于驱动程序来说，重要的只是还原后的结果，因此我们不会发送映射结果。

        让我们来看看 map 函数的实现：

        `val countLengthRDD = animalsRDD.map(animal => (animal, animal.length))`

        通过这些代码，我们可以看到 RDD 中有 (“dog”, 3), (“car”, 3), (“frog”, 4)...

        现在，让我们删除所有以 “c” 开头的动物：

        `val noCRDD = animalsRDD.filter(_.startsWith("c"))`

        查看 RDD，结果是（“cat”）。

        一些主要使用的转换包括：map()、flatMap()、filter()、sample()、union()、join()。

        重要的是不要创建[副作用操作或非关联操作](https://www.baeldung.com/scala/parallel-collections#1-parallel-limitations)。

    2. 操作

        另一方面，操作会向驱动程序返回数据。此外，操作也是启动任务计算，即执行所有转换的操作。

        查看 RDD 内容的一种方法是使用 collect 方法，它是一个操作，返回 `Seq[T]`：

        ```scala
        countLengthRDD.collect()
        // res0: Array[String] = Array((dog,3), (cat,3), (frog, 4), (horse, 5))

        noCRDD.collect()
        //res1: Array[String] = Array(cat)
        ```

        在我们的案例中，我们知道结果是一个 Array[String]。

        另一个广泛使用的操作是 reduce 方法。让我们看看它是如何工作的：

        ```scala
        val numbers = sc.parallelize(List(1, 2, 3, 4, 5))
        numbers.reduce(_ + _)
        ```

        在上面的示例中，我们看到结果是数字 15。

        主要使用的转换包括：collect()、reduce()、save()、count()。

    3. 键对 RDD

        有一种特殊类型的 RDD，即 Key-Pair RDD，有一些特殊的操作可用。

        这些方法通常是分布式 “洗牌 ”操作，用于按键对数据进行分组和聚合。

        Shuffle 是一种 Spark 机制，用于在节点间重新分配数据。Spark 要执行磁盘内数据处理、数据序列化和网络传输等代价高昂的任务，才能完成这种重新分配。此外，shuffle 还会创建中间文件，从而增加成本和内存使用量。

        为了说明洗牌，我们以 join 方法为例：

        ```scala
        val rddOne = sc.parallelize(List((1, "cat"), (2, "dog"), (3, "frog")))
        val rddTwo = sc.parallelize(List((1, "mammal"), (3, "amphibian")))
        rddOne.join(rddTwo).collect
        ```

        结果是 Array((1,(cat, mammal)), (3, (frog, amphibian)))。Spark 需要在每个分区中进行计算，然后将它们放在一起重新计算最终结果，才能得到这个结果。

4. 性能审查

    对于 Apache Spark 来说，性能是需要考虑的首要方面。

    较新的数据结构（DataFrame 和 Dataset）由于应用了各种优化，因此效率更高。不过，如前所述，RDD 在很多情况下仍然非常具体。
    另一个重要信息是，RDD 在 Scala 中的性能优于其他语言。

    此外，Spark RDD API 还提供了有关执行计划的信息。例如，让我们以一个数字 RDD 为例：

    ```scala
    val data = List(1, 2, 3, 4, 5)
    val rdd = sc.parallelize(data)

    rdd
    .filter(_ % 2 == 0)
    .map(_ * 2)
    ```

    要获取有关 RDD 系列的更多信息，我们可以使用 .toDebugString 方法：

    ```scala
    rdd.toDebugString
    res0: String =
    (8) MapPartitionsRDD[2] at map at <console>:26 []
    |  MapPartitionsRDD[1] at filter at <console>:26 []
    |  ParallelCollectionRDD[0] at parallelize at <console>:27 []
    ```

    使用数据集时，我们有 .explain 方法：

    ```scala
    val df = rdd.toDF()
    df
    .filter($"value" % 2 === 0)
    .withColumn("value", $"value" * 2)

    df.explain("formatted")
    ```

    解释方法显示了步骤和每个步骤的说明：

    ```log
    == Physical Plan ==
    * Project (4)
    +- * Filter (3)
    +- * SerializeFromObject (2)
        +- Scan (1)

    (1) Scan
    Output [1]: [obj#1]
    Arguments: obj#1: int, ParallelCollectionRDD[0] at parallelize at <console>:27

    (2) SerializeFromObject [codegen id : 1]
    Input [1]: [obj#1]
    Arguments: [input[0, int, false] AS value#2]

    (3) Filter [codegen id : 1]
    Input [1]: [value#2]
    Condition : ((value#2 % 2) = 0)

    (4) Project [codegen id : 1]
    Output [1]: [(value#2 * 2) AS value#11]
    Input [1]: [value#2]
    ```

5. 结论

    在本文中，我们看到 Spark 提供了多种数据结构，以优化数据处理的复杂性和功能。

    概括来说，RDD 适用于非结构化数据，而且性能并不重要。

    RDD 允许我们使用功能模式工作，并通过提供的 API 控制所有数据，既灵活又能从 Spark 架构中获益。

    最后，我们介绍了 Spark 本身和 RDD 的一些理论解释。我们正在学习它的用法和最佳使用案例。
