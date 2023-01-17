package com.objectcomputing.todo;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Controller("/todo")
class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get
    List<Todo> index(@NonNull Principal principal) {
        return todoRepository.findByPrincipal(principal.getName());
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post
    @Status(HttpStatus.CREATED)
    void save(@NonNull @NotNull @Valid @Body TodoForm form,
              @NonNull Principal principal) {
        todoRepository.save(form.getAction(), principal.getName());
    }

}
