

package com.redhat.kogito.gatling.util
import scala.util.Properties

object Environemnt {
  // val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")
  val baseURL: String = Properties.envOrElse("baseURL", "http://process-quarkus-example-dmartino-kogito-benchmarking.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com/")

  val numberOfUsers:Int = Properties.envOrElse("numberOfUsers", "5").toInt
  val duration:Int = Properties.envOrElse("duration", "15").toInt
  val maxDuration:Int = Properties.envOrElse("maxDuration", "5").toInt


  val maxResponseTime:Int = Properties.envOrElse("maxResponseTime", "500000").toInt // in milliseconds

}
