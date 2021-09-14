package com.redhat.kogito.gatling.simulation

import org.slf4j.{Logger, LoggerFactory}
import com.redhat.kogito.gatling.scenarios.PostOrder
import com.redhat.kogito.gatling.scenarios.StartStopBP
import com.redhat.kogito.gatling.scenarios.NoPersistBP
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environemnt, Headers}
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.language.postfixOps

class KogitoOrderProcessLoadTestSimulation extends Simulation {
  val logger = LoggerFactory.getLogger(classOf[KogitoOrderProcessLoadTestSimulation])

  logParams(logger)

  val scenarioObj = Environemnt.serviceName match {
    case "order"  => runScenarioOrder()
    case "emptyProcess"  => runScenarioStartStop()
    case "notPersistedProcess"  => runScenarioNoPersist()
    case _  => null
  }
  if(scenarioObj == null) {
    logger.error(s" scenario is invalid=${Environemnt.serviceName}")
  }

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

  def runScenarioOrder() {

    val httpConf: HttpProtocolBuilder = http.baseUrl(Environemnt.baseURL)
      .headers(Headers.commonHeader)

    if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
      setUp(PostOrder.postOrder.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else if (Environemnt.testCase == "RAMP_USERS") {
      setUp(PostOrder.postOrder.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else if (Environemnt.testCase == "CONST_USERS_PER_SEC") {
      setUp(PostOrder.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else {
      logger.error(s" invalid test case=${Environemnt.testCase}")
    }
  }

  def runScenarioStartStop() {

    val httpConf: HttpProtocolBuilder = http.baseUrl(Environemnt.baseURL)
      .headers(Headers.commonHeader)

    if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
      setUp(StartStopBP.postOrder.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else if (Environemnt.testCase == "RAMP_USERS") {
      setUp(StartStopBP.postOrder.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else if (Environemnt.testCase == "CONST_USERS_PER_SEC") {
      setUp(StartStopBP.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else {
      logger.error(s" invalid test case=${Environemnt.testCase}")
    }
  }

  def runScenarioNoPersist() {

    val httpConf: HttpProtocolBuilder = http.baseUrl(Environemnt.baseURL)
      .headers(Headers.commonHeader)

    if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
      setUp(NoPersistBP.postOrder.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else if (Environemnt.testCase == "RAMP_USERS") {
      setUp(NoPersistBP.postOrder.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else if (Environemnt.testCase == "CONST_USERS_PER_SEC") {
      setUp(NoPersistBP.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes)))
        .protocols(httpConf)
        .maxDuration(Environemnt.maxDuration minutes)
        .assertions(
          global.responseTime.max.lt(Environemnt.maxResponseTime)
        )
    }
    else {
      logger.error(s" invalid test case=${Environemnt.testCase}")
    }
  }
}