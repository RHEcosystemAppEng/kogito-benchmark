

package com.redhat.kogito.gatling.util

object Environemnt {
  // val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://process-quarkus-example-dmartino-kogito-benchmarking.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com/")

  val numberOfUsers = scala.util.Properties.envOrElse("numberOfUsers", "5")
  val duration = scala.util.Properties.envOrElse("duration", "15")
  val maxDuration = scala.util.Properties.envOrElse("maxDuration", "5")

  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "500000") // in milliseconds

}
