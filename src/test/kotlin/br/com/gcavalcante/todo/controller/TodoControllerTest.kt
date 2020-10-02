package br.com.gcavalcante.todo.controller

import br.com.gcavalcante.todo.entity.Todo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTest(
        @Autowired private val testRestTemplate: TestRestTemplate
) {

    @Test
    fun findAll() {
        val result = testRestTemplate.getForEntity("/todos", Todo::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun findById_expectNotFound() {
        val result = findById(42)

        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun create() {
        val result = create(Todo(task = "Add more validations"))

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertNotNull(result.body)

        val body = result.body!!
        assertTrue(body.id > 0)
        assertFalse(body.done)
        assertNotNull(body.createdAt)

        val findById = findById(body.id)
        assertNotNull(findById)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(body, findById.body)
    }

    @Test
    fun delete() {
        val body = create(Todo(task = "Delete this task")).body!!
        delete(body.id)

        val result = findById(body.id)
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun toggleStatus() {
        val body = create(Todo(task = "It's already done")).body!!
        assertFalse(body.done)

        val result = toggleStatus(body.id)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(body.id, result.body!!.id)
        assertTrue(result.body!!.done)
    }

    @Test
    fun toggleStatus_expectNotFound() {
        val result = toggleStatus(42)
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    fun findById(id: Long): ResponseEntity<Todo> = testRestTemplate.getForEntity("/todos/$id", Todo::class.java)

    fun create(todo: Todo): ResponseEntity<Todo> = testRestTemplate.postForEntity("/todos", todo, Todo::class.java)

    fun delete(id: Long) = testRestTemplate.delete("/todos/$id")

    fun toggleStatus(id: Long) = testRestTemplate.exchange("/todos/$id", HttpMethod.PATCH, null, Todo::class.java)
}