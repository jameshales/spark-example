import org.apache.spark.rdd.RDD

object WordCount {
  def wordCount(lines: RDD[String]): RDD[(String, Int)] =
    lines.flatMap(
        _.trim()
          .split(" ")
          .filter(_ != "")
      )
      .map(word => (word, 1))
      .reduceByKey(_ + _)
}
