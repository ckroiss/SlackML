import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * @author <a href="mailto:christian.kroiss@codecentric.de">Christian Kroiß<a/>
  */
object TestRegexp {
  def main(args: Array[String]): Unit = {
    val s =
      """
        |{ "id": C043WU6NE
        |  "name": random
        |  "messages": [
        |  {"type": "message", "user": "U2768D85S", "text": "<https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570>", "attachments": [{"title": "Blockchain revolutioniert Mobilit\u00e4t?: Verabschieden Sie sich von Ihrem Auto", "title_link": "https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570", "text": "Sie lieben Ihr Auto? Damit k\u00f6nnte es bald vorbei sein. Schuld ist die Blockchain-Technologie. Unter anderem. Wir sagen Ihnen, wie die Mobilit\u00e4t der Zukunft aussehen k\u00f6nnte.", "fallback": "Blockchain revolutioniert Mobilit\u00e4t?: Verabschieden Sie sich von Ihrem Auto", "thumb_url": "https://images.computerwoche.de/images/computerwoche/bdb/2683797/640x360.jpg", "from_url": "https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570", "thumb_width": 640, "thumb_height": 360, "service_icon": "https://www.computerwoche.de/favicon.ico", "service_name": "computerwoche.de", "id": 1}], "ts": "1494239597.255336"},
        |  {"type": "message", "user": "U04P5DP34", "text": "<https://www.youtube.com/watch?v=WZskjLq040I>", "attachments": [{"service_name": "YouTube", "service_url": "https://www.youtube.com/", "title": "Tom Scott vs Irving Finkel: The Royal Game of Ur | PLAYTHROUGH | International Tabletop Day 2017", "title_link": "https://www.youtube.com/watch?v=WZskjLq040I", "author_name": "The British Museum", "author_link": "https://www.youtube.com/user/britishmuseum", "thumb_url": "https://i.ytimg.com/vi/WZskjLq040I/hqdefault.jpg", "thumb_width": 480, "thumb_height": 360, "fallback": "YouTube Video: Tom Scott vs Irving Finkel: The Royal Game of Ur | PLAYTHROUGH | International Tabletop Day 2017", "video_html": "<iframe width=\"400\" height=\"225\" src=\"https://www.youtube.com/embed/WZskjLq040I?feature=oembed&autoplay=1&iv_load_policy=3\" frameborder=\"0\" allowfullscreen></iframe>", "video_html_width": 400, "video_html_height": 225, "from_url": "https://www.youtube.com/watch?v=WZskjLq040I", "service_icon": "https://a.slack-edge.com/2089/img/unfurl_icons/youtube.png", "id": 1}], "ts": "1494154414.897884"},
        |  {"type": "message", "user": "U2768D85S", "text": "<http://www.businessinsider.com/sheryl-sandbergs-best-advice-for-career-women-2017-5?IR=T>", "attachments": [{"service_name": "Business Insider", "title": "Sheryl Sandberg: 'Marry the nerds and the good guys'", "title_link": "http://www.businessinsider.com/sheryl-sandbergs-best-advice-for-career-women-2017-5?IR=T", "text": "This Facebook executive has one piece of relationship advice for career women, and it has to do with leaning in.", "fallback": "Business Insider: Sheryl Sandberg: 'Marry the nerds and the good guys'", "image_url": "http://static3.businessinsider.com/image/590cb625dd08959a7b8b4a9c-506-253/sheryl-sandberg-marry-the-nerds-and-the-good-guys.jpg", "from_url": "http://www.businessinsider.com/sheryl-sandbergs-best-advice-for-career-women-2017-5?IR=T", "image_width": 500, "image_height": 250, "image_bytes": 20242, "service_icon": "http://static3.businessinsider.com/assets/images/us/favicons/apple-touch-icon-57x57.png?v=BI-US-2016-03-31", "id": 1}], "ts": "1494051380.540941"},
        |  {"type": "message", "user": "U2768D85S", "text": "codecentricer bitte aber sonst: :flushed::+1: ", "ts": "1493996564.892715"}
        |}""".stripMargin

    //val s2 = "{\"type\": \"message\", \"user\": \"U2768D85S\", \"text\": \"<https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570>\", \"attachments\": [{\"title\": \"Blockchain revolutioniert Mobilit\\u00e4t?: Verabschieden Sie sich von Ihrem Auto\", \"title_link\": \"https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570\", \"text\": \"Sie lieben Ihr Auto? Damit k\\u00f6nnte es bald vorbei sein. Schuld ist die Blockchain-Technologie. Unter anderem. Wir sagen Ihnen, wie die Mobilit\\u00e4t der Zukunft aussehen k\\u00f6nnte.\", \"fallback\": \"Blockchain revolutioniert Mobilit\\u00e4t?: Verabschieden Sie sich von Ihrem Auto\", \"thumb_url\": \"https://images.computerwoche.de/images/computerwoche/bdb/2683797/640x360.jpg\", \"from_url\": \"https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570\", \"thumb_width\": 640, \"thumb_height\": 360, \"service_icon\": \"https://www.computerwoche.de/favicon.ico\", \"service_name\": \"computerwoche.de\", \"id\": 1}], \"ts\": \"1494239597.255336\"}"
    val s2 = "{\"type\": \"message\", \"user\": \"U2768D85S\", \"text\": \"<https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570>\", \"attachments\": [{\"title\": \"Blockchain revolutioniert Mobilit\\u00e4t?: Verabschieden Sie sich von Ihrem Auto\", \"title_link\": \"https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570\", \"text\": \"Sie lieben Ihr Auto? Damit k\\u00f6nnte es bald vorbei sein. Schuld ist die Blockchain-Technologie. Unter anderem. Wir sagen Ihnen, wie die Mobilit\\u00e4t der Zukunft aussehen k\\u00f6nnte.\", \"fallback\": \"Blockchain revolutioniert Mobilit\\u00e4t?: Verabschieden Sie sich von Ihrem Auto\", \"thumb_url\": \"https://images.computerwoche.de/images/computerwoche/bdb/2683797/640x360.jpg\", \"from_url\": \"https://www.computerwoche.de/a/verabschieden-sie-sich-von-ihrem-auto,3330570\", \"thumb_width\": 640, \"thumb_height\": 360, \"service_icon\": \"https://www.computerwoche.de/favicon.ico\", \"service_name\": \"computerwoche.de\", \"id\": 1}], \"ts\": \"1494239597.255336\"}"
    val r = "\"name\"\\s*:\\s*(\\S+)".r

    println(r.findFirstMatchIn(s).map(m => m.group(1)))

    val r2 = "\\{.*\\}".r
    val msgTypes: Iterator[(String, String, String)] = for {
      l <- r2.findAllIn(s)
      json = parse(l)
      JObject(child) <- json
      JField("type", JString(msgType)) <- child
      if msgType.equalsIgnoreCase("message")
      JField("text", JString(txt)) <- child
      JField("ts", JString(ts)) <- child
    } yield (msgType, txt, ts)

    println(msgTypes.mkString(", "))

    val json2 = parse(s2)
    val msgs2 = for {
      JObject(child) <- json2
      JField("type", JString(msgType)) <- child
      if msgType.equalsIgnoreCase("message")
      JField("text", JString(txt)) <- child
      JField("ts", JString(ts)) <- child
    } yield (msgType, txt, ts)
    println(msgs2)

  }

}
