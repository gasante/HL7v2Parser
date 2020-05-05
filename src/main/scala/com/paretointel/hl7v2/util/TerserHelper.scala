package com.paretointel.hl7v2.util

import ca.uhn.hl7v2.model.Segment
import ca.uhn.hl7v2.util.Terser

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks._

class TerserHelper(var _terser: Terser = null) {


  val MAX_REPEAT = 100

  def getData(terserExpression: String): String = {

    if (terserExpression == null || terserExpression.isEmpty) {
      throw new IllegalArgumentException("Terser expression is required to retrieve values")
    }

    try {
      _terser.get(terserExpression)
    } catch {
      case e: Exception => //ignore Exception
        null
    }

  }


  def getSegment(terserExpression: String): Segment = {

    if (terserExpression == null || terserExpression.isEmpty) {
      throw new IllegalArgumentException("Terser expression is required to retrieve values")
    }

    try {
      _terser.getSegment(terserExpression)
    } catch {
      case e: Exception => //ignore Exception
        null
    }

  }

  def getRepeatedSegment(terserExpression: String): List[Any] = {
    // create a Breaks object


    val lb = ListBuffer.empty[Any]
    breakable {
      for (ix <- 0 to MAX_REPEAT) {
        val exprsBuilder = new StringBuilder
        exprsBuilder.append(terserExpression.trim)
        exprsBuilder.append("(")
        exprsBuilder.append(ix)
        exprsBuilder.append(")")
        val exprData = _terser.get(exprsBuilder.toString())
        if (exprData != null && exprData.length > 9) {
          lb += exprData
        } else {
          break
        }

      }
    }
    lb.toList

  }


}
