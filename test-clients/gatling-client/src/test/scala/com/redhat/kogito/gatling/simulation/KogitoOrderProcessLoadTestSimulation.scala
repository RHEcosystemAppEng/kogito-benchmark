package com.redhat.kogito.gatling.simulation

import org.slf4j.{Logger, LoggerFactory}
import com.redhat.kogito.gatling.scenarios.PostOrder
import com.redhat.kogito.gatling.scenarios.StartStopBP
import com.redhat.kogito.gatling.scenarios.NoPersistBP
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environment, Headers, PopulationBuilderFactory}
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.structure.PopulationBuilder

import scala.language.postfixOps

class KogitoOrderProcessLoadTestSimulation extends Simulation {

  val logger = LoggerFactory.getLogger(classOf[KogitoOrderProcessLoadTestSimulation])

  logParams(logger)

  def logParams (logger: Logger) {
    logger.info("  KogitoOrderProcessLoadTestSimulation - Logging the environment variables - ")
    logger.info(s" baseURL=${Environment.baseURL}")
    logger.info(s" serviceName=${Environment.serviceName}")
    logger.info(s" testCase=${Environment.testCase}")
    logger.info(s" maxDuration=${Environment.maxDuration}")
    logger.info(s" maxResponseTime=${Environment.maxResponseTime}")
    logger.info(s" numberOfUsers=${Environment.numberOfUsers}")
    logger.info(s" duration=${Environment.duration}")
  }

  val httpConf: HttpProtocolBuilder = http.baseUrl(Environment.baseURL)
    .headers(Headers.commonHeader)

  setUp(PopulationBuilderFactory.getTestCasePopulationBuilder())
    .protocols(httpConf)
    .maxDuration(Environment.maxDuration minutes)
    .assertions(
      global.responseTime.max.lt(Environment.maxResponseTime)
    )

}