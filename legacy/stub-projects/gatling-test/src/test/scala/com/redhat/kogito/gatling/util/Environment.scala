

package com.redhat.kogito.gatling.util
import scala.util.Properties

object Environemnt {
  // the url where the tested application is running
   val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")
  //val baseURL: String = Properties.envOrElse("baseURL", "http://process-quarkus-example-dmartino-kogito-benchmarking.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com/")
  // the test case strategy useecho $d - default is WARM_UP - required parameters: numberOfUsers
  // test strategies are: CONST_CONCURRENT_USERS - required parameters: numberOfUsers, duration
  //                      RAMP_USERS - required parameters: numberOfUsers, duration
  //                      CONST_USERS_PER_SEC - required parameters: numberOfUsers, duration
  val testCase: String = Properties.envOrElse("testCase", "WARM_UP")

  // simulation specific parameters
  // The maximum duration of the simulation
  val maxDuration:Int = Properties.envOrElse("maxDuration", "5").toInt
  // Assertion: The maximum response time allowed - evaluated after simulation, if greater, simulation deemed failed
  val maxResponseTime:Int = Properties.envOrElse("maxResponseTime", "500000").toInt // in milliseconds

  // strategy/scenario specific parameters
  // depending on scenario if concurrent or total number of users
  val numberOfUsers:Int = Properties.envOrElse("numberOfUsers", "5").toInt
  // how long a scenario is to be run
  val duration:Int = Properties.envOrElse("duration", "1").toInt
}
