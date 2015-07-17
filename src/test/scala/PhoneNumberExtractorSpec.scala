package com.blikk.phonenumberapi.test;

import org.scalatest._
import com.blikk.phonenumberapi._

class PhoneNumberExtractorSpec  extends FlatSpec with Matchers {

    "A PhoneNumberExtractor" should "extract US numbers" in {
      var text = """
        Call me at 425 882-8080 for details.
        (541) 754-3010 works as well
      """
      val result = PhoneNumberExtractor.extractPhoneNumbers(text)
      result.length should be (2)
      result(0) should be (
        PhoneNumberExtraction(
          raw="425 882-8080",
          countryCode=1,
          extension="",
          nationalNumber=4258828080l,
          formatE164="+14258828080",
          formatInternational="+1 425-882-8080",
          formatNational="(425) 882-8080",
          formatRFC3966="tel:+1-425-882-8080"
        ))
        result(1) should be (
          PhoneNumberExtraction(
            raw="(541) 754-3010",
            countryCode=1,
            extension="",
            nationalNumber=5417543010l,
            formatE164="+15417543010",
            formatInternational="+1 541-754-3010",
            formatNational="(541) 754-3010",
            formatRFC3966="tel:+1-541-754-3010"
          ))
    }

    it should "extract international numbers" in {
      val text = """
        Yo, call me at my number in Paris, +33 1 23 45 67 89!
        ... or in London +44 20 7946 0018
        ... or in Germany +49 (0)261 / 30 380-80
      """
      val result = PhoneNumberExtractor.extractPhoneNumbers(text)
      result.length should be (3)
      result(0).formatInternational should be("+33 1 23 45 67 89")
      result(1).formatInternational should be("+44 20 7946 0018")
      result(2).formatInternational should be("+49 261 3038080")
    }

    it should "only return unique extractions" in {
      var text = """
        Call me at 425 882-8080 for details.
        Call me at 425 882-8080 for details.
        Call me at 425 882-8080 for details.
      """
      val result = PhoneNumberExtractor.extractPhoneNumbers(text)
      result.length should be (1)
    }

}
