package com.blikk.phonenumberapi;

import com.google.i18n.phonenumbers.{PhoneNumberUtil, PhoneNumberMatch};
import scala.collection.JavaConversions._

object PhoneNumberExtractor {

  def extractPhoneNumbers(text: String, defaultRegion: String = "US") : List[PhoneNumberExtraction] = {
    val p = PhoneNumberUtil.getInstance()
    val normalizedText = PhoneNumberExtractor.normalizeText(text)
    val extractions = p.findNumbers(normalizedText, defaultRegion).toList.map { numberMatch : PhoneNumberMatch =>
      PhoneNumberExtraction(
        numberMatch.rawString,
        numberMatch.number.getCountryCode,
        numberMatch.number.getExtension,
        numberMatch.number.getNationalNumber,
        p.format(numberMatch.number, PhoneNumberUtil.PhoneNumberFormat.E164),
        p.format(numberMatch.number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL),
        p.format(numberMatch.number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL),
        p.format(numberMatch.number, PhoneNumberUtil.PhoneNumberFormat.RFC3966)
      )
    }
    extractions.distinct
  }

  def normalizeText(text: String) : String = {
    // Replace Japanese characters
    text
      .replace("１", "1").replace("２", "2").replace("３", "3").replace("４", "4").replace("５", "5")
      .replace("６", "6").replace("７", "7").replace("８", "8").replace("９", "9").replace("０", "0")
      .replace("（", "(").replace("）", ")").replace("ー", "-")
  }
}
