package com.redhat.kogito.gatling.util

import org.slf4j.LoggerFactory
import com.redhat.kogito.gatling.scenarios.{NoPersistBP, PostGreeting, PostOrder, StartStopBP}
import com.redhat.kogito.gatling.simulation.KogitoOrderProcessLoadTestSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.redhat.kogito.gatling.util.{Environemnt, Headers}
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.structure.PopulationBuilder

import scala.language.postfixOps

object PopulationBuilderFactory{

  def getTestCasePopulationBuilder(): PopulationBuilder = {
    val logger = LoggerFactory.getLogger("PopulationBuilderFactory")
    logger.info(s"Inside getPopulationBuilder, testCase=${Environemnt.testCase}, api=${Environemnt.serviceName}")

    if("POST_GREETING".equals(Environemnt.serviceName)){
      if (Environemnt.testCase == "WARM_UP") {
        return PostGreeting.postGreeting.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
        return PostGreeting.postGreeting.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "RAMP_USERS") {
        return PostGreeting.postGreeting.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if(Environemnt.testCase == "CONST_USERS_PER_SEC") {
        return PostGreeting.postGreeting.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      }else {
        logger.error(s" invalid test case=${Environemnt.testCase}")
        return null
      }
    } else if ("order".equals(Environemnt.serviceName)){
      if (Environemnt.testCase == "WARM_UP") {
        return PostOrder.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
        return PostOrder.postOrder.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "RAMP_USERS") {
        return PostOrder.postOrder.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_USERS_PER_SEC") {
        return PostOrder.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      }else {
        logger.error(s" invalid test case=${Environemnt.testCase}")
        return null
      }
    } else if ("emptyProcess".equals(Environemnt.serviceName)){
      if (Environemnt.testCase == "WARM_UP") {
        return StartStopBP.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
        return StartStopBP.postOrder.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "RAMP_USERS") {
        return StartStopBP.postOrder.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_USERS_PER_SEC") {
        return StartStopBP.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      }else {
        logger.error(s" invalid test case=${Environemnt.testCase}")
        return null
      }
    }else if ("notPersistedProcess".equals(Environemnt.serviceName)){
      if (Environemnt.testCase == "WARM_UP") {
        return NoPersistBP.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_CONCURRENT_USERS") {
        return NoPersistBP.postOrder.inject(constantConcurrentUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "RAMP_USERS") {
        return NoPersistBP.postOrder.inject(rampUsers(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      } else if (Environemnt.testCase == "CONST_USERS_PER_SEC") {
        return NoPersistBP.postOrder.inject(constantUsersPerSec(Environemnt.numberOfUsers).during(Environemnt.duration minutes))
      }else {
        logger.error(s" invalid test case=${Environemnt.testCase}")
        return null
      }
    }
    logger.error(s" invalid service name=${Environemnt.serviceName}")
    return null
  }


}



