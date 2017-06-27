import org.apache.spark.{SparkConf, SparkContext}

object SparkFixtures {
  val DefaultAppName = "Word Count"

  def withSparkContext[A](
    config: SparkConf
  )(
    f: SparkContext => A
  ): A =
    withSparkContext(Option(config))(f)

  def withSparkContext[A](
    config: Option[SparkConf]
  )(
    f: SparkContext => A
  ): A = {
    val context   = config match {
      case None         => new SparkContext()
      case Some(config) => new SparkContext(config)
    }
    try {
      f(context)
    } finally {
      context.stop()
    }
  }

  def withLocalSparkContext[A](
    f: SparkContext => A
  ): A = {
    val config = new SparkConf()
      .setAppName(DefaultAppName)
      .setMaster("local[*]")
    withSparkContext(config)(f)
  }
}
