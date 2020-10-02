package br.com.gcavalcante.todo.controller

import br.com.gcavalcante.todo.entity.Todo
import br.com.gcavalcante.todo.service.TodoService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/todos"])
class TodoController(
        val service: TodoService
) {
    @GetMapping
    fun getAll(pageable: Pageable) = service.findAll(pageable)

    @GetMapping(path = ["/{id}"])
    fun getById(@PathVariable id: Long) = service.findById(id)

    @PostMapping
    fun create(@RequestBody todo: Todo) = service.create(todo)

    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable id: Long) = service.delete(id)

    @PutMapping(path = ["/{id}"])
    fun update(@PathVariable id: Long, @RequestBody todo: Todo) = service.update(id, todo)

    @PatchMapping(path = ["/{id}"])
    fun toggleStatus(@PathVariable id: Long) = service.toggleStatus(id)
}