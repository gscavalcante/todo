package br.com.gcavalcante.todo.repository

import br.com.gcavalcante.todo.entity.Todo
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : PagingAndSortingRepository<Todo, Long>