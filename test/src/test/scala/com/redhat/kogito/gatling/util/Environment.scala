

package com.redhat.kogito.gatling.util

object Environemnt {
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")

  val numberOfUsers = scala.util.Properties.envOrElse("numberOfUsers", "5")
  val duration = scala.util.Properties.envOrElse("duration", "15")
  val maxDuration = scala.util.Properties.envOrElse("maxDuration", "5")

  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "500000") // in milliseconds

}
