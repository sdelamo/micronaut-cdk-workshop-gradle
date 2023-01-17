package iomicronaut.hotwired.demo;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller
public class RoomsController {

    @Produces(MediaType.TEXT_HTML)
    @Get

}
