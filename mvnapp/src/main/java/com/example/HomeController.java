package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller
public class HomeController {

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    String index(HttpRequest<?> request) {
        return "Hello World";
    }
}
