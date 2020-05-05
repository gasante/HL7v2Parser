package com.paretointel.hl7v2.util

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.model.Segment
import ca.uhn.hl7v2.util.Terser
import com.paretointel.hl7v2.models._
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.joda.time.LocalDateTime

import scala.collection.mutable.ListBuffer

class HL7v2Processor {


  def parseMessage(_msg: String): String = {

    try {


      val segMainLst = ListBuffer.empty[Segments]


      val context = new DefaultHapiContext()
      val p = context.getGenericParser()
      val hapiMsg = p.parse(_msg)

      val terser = new Terser(hapiMsg)


      val terserHelper = new TerserHelper(terser)

      val segmentKeys = DataProcessor.getDataSegments()

      segmentKeys.foreach(seg => {
        val segList = ListBuffer.empty[HL7Segment]
        val segData = terserHelper.getSegment(seg.expression)
        if (seg.segment.equals("ROL1")) {
          println("Segment Data: " + segData + " Expression:" + seg.expression)
        }

        if (segData != null) {
          val lst = _processSegmentData(segData)

          //Loop for repeated segments
          segList += HL7Segment(0, lst)

          segMainLst += Segments(seg.segment, segList.toList)

        }
      })

      val msg = new HL72vMsg()

      msg.createTime = LocalDateTime.now().toString()
      msg.eventTime = DataProcessor.processTimeStamp(terserHelper.getData("/.MSH-7"))
      msg.messageType = terserHelper.getData("/.MSH-9-1")
      msg.triggerEvent = terserHelper.getData("/.MSH-9-2")
      msg.sendFacility = terserHelper.getData("/.MSH-4")
      msg.version = terserHelper.getData("/.MSH-12")
      msg.sendTime = LocalDateTime.now().toString()
      msg.messageControlID = terserHelper.getData("/.MSH-10")

      var patientIds = scala.collection.mutable.Map[String, String]()

      patientIds += ("EMPI" -> terserHelper.getData("/.PID-2"))
      patientIds += ("MRN" -> terserHelper.getData("/.PID-3"))
      patientIds += ("ALT_MRN" -> terserHelper.getData("/.PID-4"))
      patientIds += ("AccountID" -> terserHelper.getData("/.PID-18"))

      msg.patientIds = patientIds.toMap

      msg.parsedData = ParsedData(segMainLst.toList)
      implicit val formats = DefaultFormats
      write(msg)


    } catch {

      case e: Exception => println(e.getMessage)
        null

    }

  }

  private def _processSegmentData(segment: Segment): List[HL7Field] = {

    var fldList = ListBuffer.empty[HL7Field]
    try {

      val totalFields = segment.getNames.size
      val fields = segment.getNames

      for (i <- 1 to totalFields) {

        val fld = fields(i - 1)
        if (fld != null && !fld.isEmpty) {

          val obj = segment.getField(i)
          val fldData = DataProcessor.processField(i, fld, obj)
          // if(fldData.value !=null && !fldData.value.equals(""))
          fldList += fldData
        }
      }
    } catch {
      case e: Exception => //:log
    }
    fldList.toList
  }


}
