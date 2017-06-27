import org.scalatest.{FlatSpec, Matchers}

import SparkFixtures.withLocalSparkContext

class WordCountSpec extends FlatSpec with Matchers {
  "wordCount" should "return an empty output for an empty input" in
    withLocalSparkContext { context =>
      val lines = context.emptyRDD[String]
      val words = WordCount.wordCount(lines).collect()
      words should be (Seq.empty)
    }

  it should "return an empty output for an input of empty strings" in
    withLocalSparkContext { context =>
      val lines = context.makeRDD(Seq("", "", ""))
      val words = WordCount.wordCount(lines).collect()
      words should be (Seq.empty)
    }

  it should "return a count of the words appearing across each line" in
    withLocalSparkContext { context =>
      val lines = context.makeRDD(Seq("Apple Banana Carrot", "Apple", "Banana Banana"))
      val words = WordCount.wordCount(lines).collect()
      words should contain theSameElementsAs (Seq("Apple" -> 2, "Banana" -> 3, "Carrot" -> 1))
    }

  it should "ignore leading or trailing whitespace in each line" in
    withLocalSparkContext { context =>
      val lines = context.makeRDD(Seq("  Ant   Butterfly   Cockroach", "Ant  ", "   Butterfly   Butterfly   "))
      val words = WordCount.wordCount(lines).collect()
      words should contain theSameElementsAs (Seq("Ant" -> 2, "Butterfly" -> 3, "Cockroach" -> 1))
    }
}
