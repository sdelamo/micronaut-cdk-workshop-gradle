package com.example;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class BasicSimulation extends Simulation {
  public static final String API_URI = "API_URL";
  {
    if (System.getenv(API_URI) != null) {
      HttpProtocolBuilder httpProtocol = http.baseUrl(System.getenv(API_URI));
      ScenarioBuilder scn = scenario("Simple")
              .exec(http("request_1")
                      .get("/")
                      .check(status().is(200)));
      setUp(scn.injectOpen(constantUsersPerSec(10).during(30))
              .protocols(httpProtocol)
      );

    } else {
      System.out.println("Environment variable " + API_URI + " does not exist");
    }
  }
}
