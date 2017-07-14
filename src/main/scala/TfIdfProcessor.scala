import org.apache.spark.{SparkConf, SparkContext}
import org.json4s._
import org.json4s.native.JsonMethods._



/**
  * @author <a href="mailto:christian.kroiss@codecentric.de">Christian Kroiß<a/>
  */
object TfIdfProcessor {
  val CHANNEL_PATTERN = "\"name\"\\s*:\\s*(\\S+)".r

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
    val conf = new SparkConf().setAppName("TF-IDF Processor").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val dataPath = "/Users/ckroiss/ML/data/cc_slack_small"
    val data = sc.wholeTextFiles(dataPath).
      flatMap(f => chunkMessages(extractChannel(f._2), f._2)).
      flatMap(msg => parseMessage(msg)).
      collect()

    data.take(100).foreach {
      println(_)
    }

  }

}
