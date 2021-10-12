package com.redhat.kogito.gatling.util

import org.slf4j.LoggerFactory
import com.redhat.kogito.gatling.scenarios.{NoPersistBP, PostGreeting, PostOrder, StartStopBP}
import com.redhat.kogito.gatling.simulation.KogitoOrderProcessLoadTestSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environment, Headers}
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.structure.PopulationBuilder

import scala.language.postfixOps

object PopulationBuilderFactory{

  def getTestCasePopulationBuilder(): PopulationBuilder = {
    val logger = LoggerFactory.getLogger("PopulationBuilderFactory")
    logger.info(s"Inside getTestCasePopulationBuilder, testCase=${Environment.testCase}")

    if ("WARM_UP".equalsIgnoreCase(Environment.testCase)) {
      return ScenarioBuilderFactory.getTestCaseScenario().inject(constantUsersPerSec(Environment.numberOfUsers).during(Environment.duration minutes))
    } else if ("CONST_CONCURRENT_USERS".equalsIgnoreCase(Environment.testCase)) {
      return ScenarioBuilderFactory.getTestCaseScenario().inject(constantConcurrentUsers(Environment.numberOfUsers).during(Environment.duration minutes))
    } else if ("RAMP_USERS".equalsIgnoreCase(Environment.testCase)) {
      return ScenarioBuilderFactory.getTestCaseScenario().inject(rampUsers(Environment.numberOfUsers).during(Environment.duration minutes))
    } else if("CONST_USERS_PER_SEC".equalsIgnoreCase(Environment.testCase)) {
      return ScenarioBuilderFactory.getTestCaseScenario().inject(constantUsersPerSec(Environment.numberOfUsers).during(Environment.duration minutes))
    }
      logger.error(s" invalid test case=${Environment.testCase}")
      return null
  }
}



