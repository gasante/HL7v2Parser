package com.paretointel.hl7v2.service

import ca.uhn.hl7v2.model.v25.message.ADT_A01

object HL2_XML extends  App {

  import ca.uhn.hl7v2.parser.PipeParser

  //for demo purposes, we just declare a literal message string
  val ackMessageString = "MSH|^~\\&|foo|foo||foo|200108151718||ACK^A01^ACK|1|D|2.5|\rMSA|AA\r"

  val oruMsg = "MSH|^~\\&|GHH LAB|ELAB-3|GHH OE|BLDG4|200202150930||ORU^R01|CNTRL-3456|P|2.5\r" + "PID|||555-44-4444||EVERYWOMAN^EVE^E^^^^L|JONES|19620320|F|||153 FERNWOOD DR.^" + "^STATESVILLE^OH^35292||(206)3345232|(206)752-121||||AC555444444||67-A4335^OH^20030520\r" + "OBR|1|845439^GHH OE|1045813^GHH LAB|15545^GLUCOSE|||200202150730|||||||||" + "555-55-5555^PRIMARY^PATRICIA P^^^^MD^^|||||||||F||||||444-44-4444^HIPPOCRATES^HOWARD H^^^^MD\r" + "OBX|1|SN|1554-5^GLUCOSE^POST 12H CFST:MCNC:PT:SER/PLAS:QN||^182|mg/dl|70_105|H|||F\r"
  val adtMsg = new StringBuilder

  adtMsg.append("MSH|^~\\&|HL7ABLAB|HNA500|HNAM|HNAM|20090911132151||ADT^A01|Q30235031T29347435X328970|A|2.3|123\r")
  adtMsg.append("EVN|A01|20090911132100|||^DRONE_PM1^DRONE_PM^^^^^^^Personnel\r")
  adtMsg.append("NK1||ROE^MARIE^^^^|SPO||(216)123-4567||EC|||||||||||||||||||||||||||\r")
  adtMsg.append("PID|1||1357920591||IntFace1101A^WinTask^^^^^Current||19801117|M||||||||||10000476524^^^FIN^FIN NBR|100000451||||||0\r")
  adtMsg.append("PV1|1|Inpatient|CD:16067689^CD:16067691^CD:16067741^Uniontown Hospit^^Bed(s)\n    ^Uniontown Hospit||||||||||||||501455^Orr^Maggi^^^^^^External ID^Personnel^^^\n    External Identifier~25584^Orr^Maggi^^^^^^PERSONNEL PRIMARY \n    IDENTIFIER^Personnel^^^Personnel Primary Identifier|Inpatient|||||||||||||||||||||\n    Uniontown Hospit||Active|||20090911132100")
  //instantiate a PipeParser, which handles the "traditional encoding"
  val pipeParser = new PipeParser

  import ca.uhn.hl7v2.parser.DefaultXMLParser

  try { //parse the message string into a Message object
    val message = pipeParser.parse(adtMsg.toString().trim)
    //if it is an ACK message (as we know it is),  cast it to an
    // ACK object so that it is easier to work with, and change a value
    /*
  if (message.isInstanceOf[ca.uhn.hl7v2.model.v25.message.ACK]) {
    val ack = message.asInstanceOf[ca.uhn.hl7v2.model.v25.message.ACK]
    ack.getMSH.getProcessingID.getProcessingMode.setValue("P")
  }

  */
    import ca.uhn.hl7v2.DefaultHapiContext
    val context = new DefaultHapiContext

    import ca.uhn.hl7v2.parser.CanonicalModelClassFactory
    val mcf = new CanonicalModelClassFactory("2.5")

    context.setModelClassFactory(mcf)
    val parser = context.getPipeParser
    val msg = parser.parse(adtMsg.toString()).asInstanceOf[ADT_A01]

    println(s" message type is ${ msg.getMSH.getMsh3_SendingApplication.getComponent(0)}")

   // println( s" version: ${msg.getMessage.}")
    //instantiate an XML parser
    val xmlParser = new DefaultXMLParser
    //encode message in XML
    val ackMessageInXML = xmlParser.encode(message)
    //print XML-encoded message to standard out
   System.out.println(ackMessageInXML)
  } catch {
    case e: Exception =>
      e.printStackTrace()
  }


}
