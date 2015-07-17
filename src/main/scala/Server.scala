package com.blikk.phonenumberapi

import akka.actor._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._
import spray.json.DefaultJsonProtocol._

object Server extends App {
  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  case class FindNumbersRequest(
    text: String,
    countryCode: Option[String]
  )
  implicit val findNumberRequestFormat = jsonFormat2(FindNumbersRequest)
  implicit val extractionFormat = jsonFormat8(PhoneNumberExtraction)

  val routes =
    path("findNumbers") {
      post { decodeRequest { entity(as[FindNumbersRequest]) { req =>
        Console.print(".")
        complete { PhoneNumberExtractor.extractPhoneNumbers(req.text, req.countryCode.getOrElse("US")) }
      }}}
    }

  val port = Option(sys.env("PORT")).getOrElse("3000").toInt
  val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", port)
  system.awaitTermination()
}
