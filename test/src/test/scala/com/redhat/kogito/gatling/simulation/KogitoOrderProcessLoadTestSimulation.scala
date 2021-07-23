package com.redhat.kogito.gatling.simulation

import com.redhat.kogito.gatling.scenarios.PostOrder
import com.redhat.kogito.gatling.util.{Environemnt, Headers}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.language.postfixOps
import scala.concurrent.duration._

class KogitoOrderProcessLoadTestSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http.baseUrl(Environemnt.baseURL)
                      .headers(Headers.commonHeader)

  setUp(PostOrder.postOrder.inject(atOnceUsers(10)))
    .protocols(httpConf)
    .maxDuration(5 minutes)
    .assertions(
      global.responseTime.max.lt(Environemnt.maxResponseTime.toInt)
    )


}