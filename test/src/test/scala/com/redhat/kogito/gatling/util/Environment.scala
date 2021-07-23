

package com.redhat.kogito.gatling.util

object Environemnt {
  //val baseURL = scala.util.Properties.envOrElse("baseURL", "http://process-quarkus-example-mongo-dmartino-kogito-benchmarking.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com")
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")

  val users = scala.util.Properties.envOrElse("numberOfUsers", "5000")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "500000") // in milliseconds

}
