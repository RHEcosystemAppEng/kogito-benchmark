package com.redhat.kogito.gatling.simulation

import org.slf4j.{Logger, LoggerFactory}
import com.redhat.kogito.gatling.scenarios.PostOrder
import com.redhat.kogito.gatling.scenarios.StartStopBP
import com.redhat.kogito.gatling.scenarios.NoPersistBP
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environemnt, Headers, PopulationBuilderFactory}
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.structure.PopulationBuilder

import scala.language.postfixOps

class KogitoOrderProcessLoadTestSimulation extends Simulation {

  val logger = LoggerFactory.getLogger(classOf[KogitoOrderProcessLoadTestSimulation])

  logParams(logger)

  def logParams (logger: Logger) {
    logger.info("  KogitoOrderProcessLoadTestSimulation - Logging the environment variables - ")
    logger.info(s" baseURL=${Environemnt.baseURL}")
    logger.info(s" serviceName=${Environemnt.serviceName}")
    logger.info(s" testCase=${Environemnt.testCase}")
    logger.info(s" maxDuration=${Environemnt.maxDuration}")
    logger.info(s" maxResponseTime=${Environemnt.maxResponseTime}")
    logger.info(s" numberOfUsers=${Environemnt.numberOfUsers}")
    logger.info(s" duration=${Environemnt.duration}")
  }

  val httpConf: HttpProtocolBuilder = http.baseUrl(Environemnt.baseURL)
    .headers(Headers.commonHeader)

  setUp(PopulationBuilderFactory.getTestCasePopulationBuilder())
    .protocols(httpConf)
    .maxDuration(Environemnt.maxDuration minutes)
    .assertions(
      global.responseTime.max.lt(Environemnt.maxResponseTime)
    )

}