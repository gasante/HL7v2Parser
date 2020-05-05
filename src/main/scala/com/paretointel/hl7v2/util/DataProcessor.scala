package com.paretointel.hl7v2.util

import ca.uhn.hl7v2.model.Type
import com.paretointel.hl7v2.models.HL7Field
import com_paretointel_hl7v2_models.{ClinicianName, ExtendedCode, ExtendedPersonName, HL7Address, MessageType, PersonLocation, SegementKey}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DataProcessor {


  /**
    *
    * @param addr
    * @return
    */
  def processAddress(addr: String): HL7Address = {
    //
    val SPLIT_CHARACTER = '^'
    val addrSplit = addr.trim.replace("AD[", " ").replaceAll("]", "").split(SPLIT_CHARACTER)

    val addressLine1  = if (addrSplit != null) addrSplit(0).trim else null
    val addressLine2  = if (addrSplit != null && addrSplit.length > 1) addrSplit(1).trim else ""
    val city          = if (addrSplit != null && addrSplit.length > 2) addrSplit(2).trim  else ""
    val state         = if (addrSplit != null && addrSplit.length > 3) addrSplit(3).trim  else ""
    val zip           = if (addrSplit != null && addrSplit.length > 4) addrSplit(4).trim  else ""
    val country       = if (addrSplit != null && addrSplit.length > 5) addrSplit(5).trim  else ""

    HL7Address(addressLine1, addressLine2, city, state, zip, country)

  }

  /* case class ExtendedCode(
                         identifier: String,
                         text: String,
                         codingSystem: String,
                         alternateIdentifier: String,
                         alternateText: String,
                         alternateCodingSystem: String
                       )*/

  def processExtendedCode(data:String): ExtendedCode ={

    val SPLIT_CHARACTER = '^'
    val codeSplit = data.trim.replace("CE[", " ").replaceAll("]", "").split(SPLIT_CHARACTER)

    val identifier            = if (codeSplit != null) codeSplit(0) else null
    val text                  = if (codeSplit != null && codeSplit.length > 1) codeSplit(1).trim else ""
    val codingSystem          = if (codeSplit != null && codeSplit.length > 2) codeSplit(2).trim else ""
    val alternateIdentifier   = if (codeSplit != null && codeSplit.length > 3) codeSplit(3).trim else ""
    val alternateText         = if (codeSplit != null && codeSplit.length > 4) codeSplit(4).trim else ""
    val alternateCodingSystem = if (codeSplit != null && codeSplit.length > 5) codeSplit(5).trim else ""

    ExtendedCode(identifier, text, codingSystem, alternateIdentifier, alternateText, alternateCodingSystem)
  }

  def processMessageType(mgsType: String): MessageType = {
    //
    val SPLIT_CHARACTER = '^'
    val mgsTypeSplit = mgsType.trim.replace("CM_MSG[", " ").replaceAll("]", "").split(SPLIT_CHARACTER)

    val messageType   = if (mgsTypeSplit != null) mgsTypeSplit(0).trim else null
    val triggerEvent  = if (mgsTypeSplit != null && mgsTypeSplit.length > 1) mgsTypeSplit(1).trim else ""
    val structure     = if (mgsTypeSplit != null && mgsTypeSplit.length > 2) mgsTypeSplit(2).trim else ""


    MessageType(messageType, triggerEvent,structure)

  }


  /**
    *
    * @param _name
    * @return
    */
  def processPersonName(_name: String): ExtendedPersonName = {
    //
    val SPLIT_CHARACTER = '^'
    val _nameSplit = _name.trim.replace("PN[", " ").replaceAll("]", "").split(SPLIT_CHARACTER)

    val lastName    = if (_nameSplit != null) _nameSplit(0) else ""
    val firstName   = if (_nameSplit != null && _nameSplit.length > 1) _nameSplit(1).trim else ""
    val middleName  = if (_nameSplit != null && _nameSplit.length > 2) _nameSplit(2).trim else ""
    val suffix      = if (_nameSplit != null && _nameSplit.length > 3) _nameSplit(3).trim else ""
    val prefix      = if (_nameSplit != null && _nameSplit.length > 4) _nameSplit(4).trim else ""
    val degree      = if (_nameSplit != null && _nameSplit.length > 5) _nameSplit(5).trim else ""

    ExtendedPersonName(lastName, firstName, middleName, suffix, prefix, degree)

  }

  /**
    *
    * @param _name
    * @return
    */
  def processClinician(_name:String):ClinicianName={
    val SPLIT_CHARACTER = '^'
    val _nameSplit = _name.trim.replace("CN[", " ").replaceAll("]", "").trim.split(SPLIT_CHARACTER)

    val identifier = if (_nameSplit != null) _nameSplit(0) else null
    val lastName   = if (_nameSplit != null && _nameSplit.length > 1) _nameSplit(1) else null
    val firstName  = if (_nameSplit != null && _nameSplit.length > 2) _nameSplit(2) else ""
    val middleName  = if (_nameSplit != null && _nameSplit.length > 3) _nameSplit(3) else ""
    val suffix      = if (_nameSplit != null && _nameSplit.length > 4) _nameSplit(4) else ""
    val prefix      = if (_nameSplit != null && _nameSplit.length > 5) _nameSplit(5) else ""
    val degree      = if (_nameSplit != null && _nameSplit.length > 6) _nameSplit(6) else ""

    ClinicianName(identifier, lastName, firstName, middleName, suffix, prefix, degree)

  }

  /**
    *
    * @param _Location
    * @return
    */
  def processInternalLocation(_Location:String):PersonLocation ={
    val SPLIT_CHARACTER = '^'
    val _locationSplit = _Location.trim.replace("CM_INTERNAL_LOCATION[", " ").replaceAll("]", "").trim.split(SPLIT_CHARACTER)

    val pointOfCare     = if (_locationSplit != null) _locationSplit(0) else null
    val room            = if (_locationSplit != null && _locationSplit.length > 1) _locationSplit(1) else null
    val bed             = if (_locationSplit != null && _locationSplit.length > 2) _locationSplit(2) else ""
    val facility        = if (_locationSplit != null && _locationSplit.length > 3) _locationSplit(3) else ""
    val locationStatus  = if (_locationSplit != null && _locationSplit.length > 4) _locationSplit(4) else ""
    val locationType    = if (_locationSplit != null && _locationSplit.length > 5) _locationSplit(5) else ""
    val building        = if (_locationSplit != null && _locationSplit.length > 6) _locationSplit(6) else ""
    val floor           = if (_locationSplit != null && _locationSplit.length > 6) _locationSplit(6) else ""

    PersonLocation(
      pointOfCare, room, bed, facility, locationStatus,
      locationType, building, floor)

  }


  /**
    *
    * @param _data
    * @param strip
    * @return
    */
  def processStripData(_data:String, strip:String):String = {
    _data.trim.replace(strip, "").replaceAll("]", "")
  }

  /**
    *
    * @param indx
    * @param fieldName
    * @param data
    * @return
    */
  def processField(indx: Int, fieldName: String, data: Array[Type]): HL7Field = {

    val _data= if(data.length>0) _parseData(data(0).toString)
    else if(data.length>1) data.toList else data.mkString(",")

    HL7Field(indx, _standardizedFieldName(fieldName), _data)
  }

  /**
    *
    * @param fieldName
    * @return
    */
  private def _standardizedFieldName(fieldName:String):String ={

    fieldName.replaceAll(" /","").replaceAll(" - ","_").replaceAll(" ","_")
      .replaceAll("'","")
      .replaceAll("\\(","").replaceAll("&","").replaceAll("/","")
      .replaceAll("\\)","").toLowerCase()

  }

  /**
    *
    * @param _data
    * @return
    */
   def processTimeStamp(_data:String): String ={

    try {
      val DATE_TIME_PATTERN = DateTimeFormat.forPattern("yyyyMMddHHmm")
      val ts = _data.trim.replace("TS[", "").replaceAll("]", "")
      DateTime.parse(ts, DATE_TIME_PATTERN).toLocalDateTime.toString
    }catch {
      case e:Exception => _data.trim.replace("TS[", "").replaceAll("]", "")
    }
  }

  /**
    *
    * @param _data
    * @return
    */
  private def _parseData(_data:String):Any={

    if(_data !=null && _data.startsWith("AD[")){
      processAddress(_data)
    }else if(_data !=null && _data.startsWith("PN[")){
      processPersonName(_data)
    } else if(_data !=null && (_data.startsWith("CX[") || _data.startsWith("CK["))){
      processStripData(_data,"CK[")
    } else if(_data !=null && (_data.startsWith("CN[") || _data.startsWith("XCN["))){
      processClinician(_data)
    }else if(_data !=null && _data.startsWith("CM_DLD[")){
      processStripData(_data,"CM_DLD[")
    } else if(_data !=null && _data.startsWith("CM_PAT_ID[")){
      processStripData(_data,"CM_PAT_ID[")
    }else if(_data !=null && _data.startsWith("CM_FINANCE[")){
      processStripData(_data,"CM_FINANCE[")
    }else if(_data !=null && _data.startsWith("CM_PAT_ID_0192[")){
      processStripData(_data,"CM_PAT_ID_0192[")
    }else if(_data !=null && _data.startsWith("TS[")){
      processTimeStamp(_data)
    }else if(_data !=null && _data.startsWith("CM_INTERNAL_LOCATION[")){
      processInternalLocation(_data)
    }else if(_data !=null && _data.startsWith("CM_MSG[")){
      processMessageType(_data)
    }else if(_data !=null && _data.startsWith("CE[")){
      processExtendedCode(_data)
    }else if(_data !=null && _data.startsWith("XPN[")){
      processPersonName(_data)
    }else if(_data !=null && _data.startsWith("HD[")){
      processStripData(_data,"HD[")
    }else if(_data !=null && _data.startsWith("M_AUI[")){
      processStripData(_data,"M_AUI[")
    }else _data
  }
//CM_AUI[

  def getDataSegments():List[SegementKey] = {
    List(
      SegementKey("MSH", "/.MSH", false),
      SegementKey("ENV", "/.ENV", false),
      SegementKey("PID", "/.PID", false),
      SegementKey("PD1", "/.PD1", false),
      SegementKey("ROL", "/.ROL", true),
      SegementKey("NK1", "/.NK1", true),
      SegementKey("PV1", "/.PV1", false),
      SegementKey("PV2", "/.PV2", false),
      SegementKey("ROL", "/.ROL", true),
      SegementKey("DB1", "/.DB1", true),
      SegementKey("OBX", "/.OBX", true),
      SegementKey("AL1", "/.AL1", true),
      SegementKey("DG1", "/.DG1", true),
      SegementKey("DRG", "/.DRG", true),
      SegementKey("PRI", "/.PRI", true),
      SegementKey("GT1", "/.GT1", true),
      SegementKey("IN1", "/.IN1", true),
      SegementKey("IN2", "/.IN2", true),
      SegementKey("IN3", "/.IN3", true),
      SegementKey("ACC", "/.ACC", true),
      SegementKey("UB1", "/.UB1", true),
    )
  }

}
