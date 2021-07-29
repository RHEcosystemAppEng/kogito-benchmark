

package com.redhat.kogito.gatling.util

object Environemnt {
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")

  val numberOfUsers = scala.util.Properties.envOrElse("numberOfUsers", "5000")
  val duration = scala.util.Properties.envOrElse("duration", "10")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "500000") // in milliseconds

}
