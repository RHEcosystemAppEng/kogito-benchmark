package com.redhat.kogito.gatling.simulation

import com.redhat.kogito.gatling.scenarios.PostOrder
import com.redhat.kogito.gatling.scenarios.travel.TravelRequest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environment, Headers}
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.language.postfixOps

class KogitoTravelAgencyLoadTestSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder = http.baseUrl(Environment.baseURL)
      .headers(Headers.commonHeader)

    setUp(TravelRequest.postTravel.inject(atOnceUsers(1)))
      .protocols(httpConf)
      .maxDuration(5 minutes)
      .assertions(
        global.responseTime.max.lt(Environment.maxResponseTime.toInt)
      )



  }