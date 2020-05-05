
import com.paretointel.hl7v2.models._
import com.paretointel.hl7v2.util.DataProcessor
import net.liftweb.json.Serialization.write
import net.liftweb.json._
import org.joda.time.LocalDateTime

object AppSampleJsonBuilder {

  def main(args: Array[String]): Unit = {
    val msg = new HL72vMsg()

    msg.createTime = LocalDateTime.now().toString()
    msg.eventTime = LocalDateTime.now().toString()
    msg.messageType = "ADT"
    msg.triggerEvent = "A01"
    msg.sendFacility = "Asante Hospital"
    msg.sendTime = LocalDateTime.now().toString()
    msg.messageControlID = "ctrl-3837393"

    var patientIds = scala.collection.mutable.Map[String, String]()

    patientIds += ("EMPI" -> "EMPI-12443")
    patientIds += ("MRN" -> "M-12443")
    patientIds += ("AccountID" -> "ACC-233u32")

    msg.patientIds = patientIds.toMap


    /*
    * case class ParsedData(var segments: Segments = null)

      case class Segments(var segmentId:String=null, var segments: List[HL7Segment])

      case class HL7Segment( var seq:Int= 0 , var fields: List[HL7Field] = null)

      case class HL7Field(var position: Int = -1, name: String = null, value: Any)
    * */


    var seg1 = new HL7Segment

    seg1.seq = 0
    seg1.fields = List(HL7Field(1, "FieldSeparator", "|"), HL7Field(2, "Encoding", "U-39"), HL7Field(3, "SendingAApplication", "Test"))

    var segments1 = new Segments

    segments1.segmentId = "MSH"
    segments1.segmentData = List(seg1)


    var seg2 = new HL7Segment
    val addrData = "AD[564 SPRING ST^^NEEDHAM^MA^02494^US]"
    val address = DataProcessor.processAddress(addrData) // HL7Address("3332 Harrison Street","", "Evanston","IL", "60202","US" )

    seg2.seq = 0
    seg2.fields = List(HL7Field(1, "SetId", "1"), HL7Field(2, "PatientID", "ID-3938"),
      HL7Field(3, "AltID", "Al-38373"), HL7Field(4, "Address", address))

    var segments2 = new Segments

    segments2.segmentId = "PID"
    segments2.segmentData = List(seg2)


    msg.parsedData = ParsedData(List(segments1, segments2))

    implicit val formats = DefaultFormats
    val jsonString = write(msg)

    println(jsonString)

  }

}
