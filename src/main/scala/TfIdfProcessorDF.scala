import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer, CountVectorizer}


/**
  * @author <a href="mailto:christian.kroiss@codecentric.de">Christian Kroiß<a/>
  */
object TfIdfProcessorDF {
  val CHANNEL_PATTERN = "\"name\"\\s*:\\s*\"(\\S+)\"".r

  def extractChannel(msg: String) = {
    CHANNEL_PATTERN.findFirstMatchIn(msg).map(m => m.group(1)).getOrElse("UNDEFINED")
  }

  def chunkMessages(channel: String, content: String) = {
    val start = content.indexOf("[")
    val end = content.lastIndexOf("]")
    val s = if (start >= 0 && end >= 0) content.substring(start, end) else ""
    "\\{.*\\}".r.findAllIn(s).
      map(msg => (channel, msg))
  }

  def parseMessage(msg: (String, String)) = {
    val json = parse(msg._2)
    val parsedMsg: Iterable[Message] = for {
      JObject(child) <- json
      JField("type", JString(msgType)) <- child
      if msgType.equalsIgnoreCase("message")
      JField("text", JString(txt)) <- child
      JField("ts", JString(ts)) <- child
    } yield Message(msg._1, txt, ts.toDouble)
    parsedMsg
  }


  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("TF/IDF Processor")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    import spark.implicits._
    val ds = spark.sparkContext.wholeTextFiles("/Users/ckroiss/ML/data/cc_slack/").toDS()
    ds.show()
    println(s"count: ${ds.count()}")
    val ds2 = ds.flatMap(f => chunkMessages(extractChannel(f._2), f._2)).
      flatMap(msg => parseMessage(msg))
    ds2.show()
    println(s"count2: ${ds2.count()}")

    //ds2.withColumn("ts2", ds2(""))

    val m = min($"ts") * 2
    val timesDs = ds2.select(m, max($"ts"))
    timesDs.show()

    val tokenizer = new Tokenizer().setInputCol("msg").setOutputCol("words")
    val wordsData = tokenizer.transform(ds2)
    wordsData.show()

    val vectorizer = new CountVectorizer().setInputCol("words")
      .setOutputCol("vector")

    val model1 = vectorizer.fit(wordsData)
    val df = model1.transform(wordsData)


    val hashingTf = new HashingTF().setInputCol("words").setOutputCol("rawFeatures")
    val featurizedData = hashingTf.transform(df)
    featurizedData.show()

    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)

    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.select("channel", "features").show()


    featurizedData.groupBy($"channel").count().show()

    rescaledData.write.parquet("/Users/ckroiss/ML/data/cc_slack.parquet")


  }

}
