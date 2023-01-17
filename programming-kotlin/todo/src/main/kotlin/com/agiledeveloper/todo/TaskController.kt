package com.agiledeveloper

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/task")
class TaskController {
    @Get
    fun tasks() = "to be implemented"
}