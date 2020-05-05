package com.paretointel.hl7v2.models

case class HL72vMsg(var eventId:String = java.util.UUID.randomUUID.toString,
                     var eventTime: String = null,
                    var sendFacility: String = null,
                    var sendTime: String = null,
                    var messageType: String = null,
                    var triggerEvent: String = null,
                    var version:String = null,
                    var createTime: String = null,
                    var messageControlID: String = null,
                    var EnterpriseId:String = null,
                    var patientIds: Map[String, String] = Map.empty[String, String],
                    var parsedData: ParsedData = null

                   )

case class ParsedData(var segments: List[Segments] = List.empty[Segments])

case class Segments(var segmentId:String=null, var segmentData: List[HL7Segment]  =  List.empty[HL7Segment])

case class HL7Segment( var seq:Int= 0 , var fields: List[HL7Field] = null)

case class HL7Field(var position: Int = -1, name: String = null, value: Any)




