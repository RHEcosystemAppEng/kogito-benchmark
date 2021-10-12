package com.redhat.kogito.gatling.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object PostGreeting {

  val postGreetingJsonString: String =
    """
      |{
      |  "message": "Test Message"
      |}
      |""".stripMargin

  val postGreetingHttp: HttpRequestBuilder = http("HTTP Post Greeting")
    .post("/greeting/post")
    .body(StringBody(postGreetingJsonString)).asJson
    .check(status is 201)

  val postGreeting: ScenarioBuilder = scenario(postGreetingHttp.toString)
    .exec(postGreetingHttp)
}
