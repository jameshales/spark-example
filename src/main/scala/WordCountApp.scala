import org.apache.spark.{SparkConf, SparkContext}

import SparkFixtures.withSparkContext

object WordCountApp {
  val AppName = "Word Count"

  def main(args: Array[String]): Unit = {
    if (args.size == 2 && (args(0) == "--local" || args(0) == "--remote")) {
      val config =
        if (args(0) == "--local")
          Some(
            new SparkConf()
              .setAppName(AppName)
              .setMaster("local[*]")
          )
        else
          None
      withSparkContext(config) { context =>
        printWordCount(context, args(1))
      }
    } else {
      printUsage()
    }
  }

  def printUsage(): Unit =
    System.err.println("Usage: WordCount (--local|--remote) FILE")

  def printWordCount(context: SparkContext, fileName: String): Unit = {
    val lines = context.textFile(fileName, 2)
    val words = WordCount.wordCount(lines).collect()

    for ((word, count) <- words) {
      println(s"$word: $count")
    }
  }
}
