package com.example;

import com.example.core.ApplicationTypeProvider;
import com.example.core.RuntimeProvider;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller
public class HomeController {

    private final RuntimeProvider runtimeProvider;
    private final ApplicationTypeProvider applicationTypeProvider;
    public HomeController(RuntimeProvider runtimeProvider, ApplicationTypeProvider applicationTypeProvider) {
        this.runtimeProvider = runtimeProvider;
        this.applicationTypeProvider = applicationTypeProvider;
    }



    @Produces(MediaType.TEXT_PLAIN)
    @Get
    String index(HttpRequest<?> request) {
        return "App Type: " + applicationTypeProvider.getApplicationType()
                + runtimeProvider.runtime().map(runtime -> " Runtime: " + runtime).orElse("") +
                ". Hello CDK with Micronaut!, You have hit " + request.getPath();
    }

}
