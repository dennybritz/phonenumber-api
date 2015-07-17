package com.blikk.phonenumberapi;

case class PhoneNumberExtraction(
  raw: String,
  countryCode: Int,
  extension: String,
  nationalNumber: Long,
  formatE164: String,
  formatInternational: String,
  formatNational: String,
  formatRFC3966: String
)
