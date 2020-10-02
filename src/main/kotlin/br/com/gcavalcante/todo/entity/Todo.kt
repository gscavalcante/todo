package br.com.gcavalcante.todo.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Todo(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val task: String = "",
        val done: Boolean = false
) {
        val createdAt: LocalDateTime = LocalDateTime.now()
}