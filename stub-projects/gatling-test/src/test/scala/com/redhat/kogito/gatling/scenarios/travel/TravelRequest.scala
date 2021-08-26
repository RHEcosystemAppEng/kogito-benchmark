/**
 *
 * This simulation is dependent on below kogito business service.
 * https://github.com/dmartinol/kogito-examples/tree/kogito-benchmarking/process-mongodb-persistence-quarkus
 *
 * This simulation try to emulate below API call.
 * curl -d '{"approver" : "john", "order" : {"orderNumber" : "12345", "shipped" : false}}' -H "Content-Type: application/json" -X POST http://localhost:8080/orders
 *
 *http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/orders-resource/createResource_orders
 */
package com.redhat.kogito.gatling.scenarios.travel

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object TravelRequest {

  val postTravelRequestHttp: HttpRequestBuilder = http("Request 1 - HTTP Post Travel")
    .post("/travels")
    .body(StringBody(
      s"""
         |{
         |    "trip": {
         |      "city": "My City",
         |      "country": "US",
         |      "begin": "2021-07-08T00:00:00.000+02:00",
         |      "end": "2021-08-31T00:00:00.000+02:00",
         |      "visaRequired": true
         |    },
         |    "traveller": {
         |      "firstName": "My First Name",
         |      "lastName": "My Last Name",
         |      "email": "myEmail@test.com",
         |      "nationality": "Polish",
         |      "address": {
         |        "street": "My Street",
         |        "city": "My City",
         |        "zipCode": "383434",
         |        "country": "Poland"
         |      }
         |    }
         |}
          """.stripMargin))
    .asJson
    .check(status is 201)
    .check(jsonPath("$.id").saveAs("travelId"))


  val getTravelTasks: HttpRequestBuilder = http("Request 2 - Get Travel Tasks for travelId=${travelId} ")
    .get("/travels/${travelId}/tasks")
    .check(status is 200)
    .check(jsonPath("$..id").saveAs("travelTaskId"))

  val postVisaApplicationRequestHttp: HttpRequestBuilder = http("Request 3 - HTTP Post Visa Application for travelId=${travelId}, travelTaskId=${travelTaskId}")
    .post("/travels/${travelId}/VisaApplication/${travelTaskId}")
    .body(StringBody(
      s"""
         |{
         |	"visaApplication": {
         |		"country": "US",
         |		"city": "My City",
         |		"firstName": "My First Name",
         |		"lastName": "My Last Name",
         |		"duration": 10,
         |		"passportNumber": "PP3423434",
         |		"nationality": "Polish"
         |	}
         |}
          """.stripMargin))
    .asJson
    .check(status is 200)

  val getTravelConfirmTasks: HttpRequestBuilder = http("Request 4 - Get Travel Tasks for travelId=${travelId} ")
    .get("/travels/${travelId}/tasks")
    .check(status is 200)
    .check(jsonPath("$..id")
    .saveAs("travelConfirmTaskId"))


  val postConfirmTravelRequestHttp: HttpRequestBuilder = http("Request 5 - HTTP Post Confirm Travel for travelId=${travelId}, travelTaskId=${travelConfirmTaskId}")
    .post("/travels/${travelId}/ConfirmTravel/${travelConfirmTaskId}")
    .body(StringBody(
      s"""
         |{
         |}
          """.stripMargin))
    .asJson
    .check(status is 200)


  val postTravel: ScenarioBuilder = scenario("Travel Request Scenario ")
    .exec(postTravelRequestHttp)
    .exec(getTravelTasks)
    .exec(postVisaApplicationRequestHttp)
    .pause(15)
    .exec(getTravelConfirmTasks)
    .exec(postConfirmTravelRequestHttp)
}