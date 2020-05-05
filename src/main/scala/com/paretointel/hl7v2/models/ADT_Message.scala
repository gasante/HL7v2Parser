package com.paretointel.hl7v2.models

import com_paretointel_hl7v2_models._

import scala.collection.mutable.ListBuffer

class ADT_Message {

  private var _msh:MSH =null
  private var _env:EVN = null
  private var _pid:PID = null
  private var _pid1:PID1= null

  private var nk1s = ListBuffer.empty[NK1]
  private var _pv1:PV1 = null
  private var _pv2:PV2 = null
  private var _arvs = ListBuffer.empty[ARV]
  private var _disabilities = ListBuffer.empty[DB1]
  private var _allergies = ListBuffer.empty[AL1]

  private var _diagnosis = ListBuffer.empty[DG1]
  private var _drg:DRG = null
  private var _procedures = ListBuffer.empty[PR1]
  private var _obx = ListBuffer.empty[OBX]
  private var _gt1 = ListBuffer.empty[GT1]

  private var _in1 = ListBuffer.empty[IN1]
  private var _in2 = ListBuffer.empty[IN2]
  private var _in3 = ListBuffer.empty[IN3]

  private var _acc:ACC = null
  private var _pda:PDA = null


  def MSH = _msh
  def MSH_=(_nMsh:MSH): Unit={
    if(_nMsh !=null) _msh = _nMsh
  }







}
