package com.redhat.kogito.gatling.simulation


import org.slf4j.LoggerFactory
import com.redhat.kogito.gatling.scenarios.PostOrder
import com.redhat.kogito.gatling.scenarios.travel.TravelRequest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environemnt, Headers}
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.language.postfixOps


class KogitoOrderProcessLoadTestSimulation extends Simulation {
  val logger = LoggerFactory.getLogger(classOf[KogitoOrderProcessLoadTestSimulation])

  logger.info("  KogitoOrderProcessLoadTestSimulation - Logging the environemnt variables - ")
  logger.info(s" baseURL=${Environemnt.baseURL}")
  logger.info(s" numberOfUsers=${Environemnt.numberOfUsers}")
  logger.info(s" duration=${Environemnt.duration}")
  logger.info(s" maxDuration=${Environemnt.maxDuration}")
  logger.info(s" maxResponseTime=${Environemnt.maxResponseTime}")

  val httpConf: HttpProtocolBuilder = http.baseUrl(Environemnt.baseURL)
                      .headers(Headers.commonHeader)

  setUp(PostOrder.postOrder.inject(atOnceUsers(Environemnt.numberOfUsers)))
    .protocols(httpConf)
    .maxDuration(Environemnt.maxDuration minutes)
    .assertions(
      global.responseTime.max.lt(Environemnt.maxResponseTime)
    )


}