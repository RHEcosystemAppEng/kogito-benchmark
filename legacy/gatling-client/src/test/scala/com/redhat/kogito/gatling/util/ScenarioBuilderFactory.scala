package com.redhat.kogito.gatling.util

import com.redhat.kogito.gatling.scenarios.{NoPersistBP, PostGreeting, PostOrder, StartStopBP}
import io.gatling.core.Predef._
import io.gatling.core.structure.{ScenarioBuilder}
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.language.postfixOps

object ScenarioBuilderFactory{

  def getTestCaseScenario(): ScenarioBuilder = {
    val logger = LoggerFactory.getLogger("PopulationBuilderFactory")
    logger.info(s"Inside getTestCaseScenario serviceName=${Environment.serviceName}")

    if("stubRestApi".equalsIgnoreCase(Environment.serviceName)){
        return PostGreeting.postGreeting
    } else if ("order".equalsIgnoreCase(Environment.serviceName)){
        return PostOrder.postOrder
    } else if ("emptyProcess".equalsIgnoreCase(Environment.serviceName)){
        return StartStopBP.startStopBp
    }else if ("notPersistedProcess".equalsIgnoreCase(Environment.serviceName)){
        return NoPersistBP.noPersistBp
    }
    logger.error(s" invalid service name=${Environment.serviceName}")
    return null
  }


}



