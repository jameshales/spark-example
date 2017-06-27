Spark Word Count Example
========================

An example Spark application with SBT configuration and unit tests. Implements
the obligatory "Word Count" example in Spark.

Overview
--------

- `/build.sbt` - SBT configuration for the project. Includes `spark-core` and `scalatest` as dependencies. Sets `fork := true`, to work around an issue with the class loader when running or testing Spark applications in SBT.
- `/project/build.properties` - Sets the SBT version to use.
- `/src/main/resources/log4j.properties` - Sets logging level to `WARN`, to minimise log output when running or testing Spark applications.
- `/src/main/scala/SparkFixtures.scala` - Fixtures for creating a `SparkContext`, passing it into a closure, and then stopping the `SparkContext` once the closure has finished. Re-used in application code and tests.
- `/src/main/scala/WordCount.scala` - Pure function on `RDD`s for counting the occurrences of words in a multi-line input.
- `/src/main/scala/WordCountApp.scala` - Application that accepts a file name as a command-line argument, starts a `SparkContext`, reads the file, calculates the word count, and prints the results to `stdout`. It can run in local mode with hard-coded configuration (`master = "local[*]"`), or remote mode with configuration supplied from the environment or `spark-submit`.
- `/src/test/scala/WordCountSpec.scala` - Tests the pure code in `WordCount` using ScalaTest and the fixtures in `SparkFixtures`.

Usage
-----

To compile:

```sh
sbt compile
```

To run tests:

```sh
sbt test
```

To run locally:

```sh
sbt 'run --local FILE'
```

To run remotely:

```sh
sbt package
spark-submit target/scala-2.11/word-count_2.11-1.0.jar --remote README.md
```
