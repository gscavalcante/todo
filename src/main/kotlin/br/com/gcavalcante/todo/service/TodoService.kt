package br.com.gcavalcante.todo.service

import br.com.gcavalcante.todo.entity.Todo
import br.com.gcavalcante.todo.exception.EntityNotFoundException
import br.com.gcavalcante.todo.repository.TodoRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TodoService(
        val repository: TodoRepository
) {
    fun findAll(pageable: Pageable) = repository.findAll(pageable)

    fun findById(id: Long): Todo = repository
            .findById(id)
            .orElseThrow{ EntityNotFoundException() }

    fun create(todo: Todo) = repository.save(todo)

    fun delete(id: Long) = repository.deleteById(id)

    fun update(id: Long, todo: Todo) = repository.save(todo)

    fun toggleStatus(id: Long) = findById(id)
            .let { it.copy(done = !it.done) }
            .let { update(id, it) }
}
