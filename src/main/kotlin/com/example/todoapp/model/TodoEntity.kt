package com.example.todoapp.model

import com.example.todoapp.model.dto.TodoDto
import com.example.todoapp.model.dto.UpdateTodoDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "todos")
data class TodoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    var title: String?,

    @Lob
    @Type(type = "org.hibernate.type.TextType") // for PostgreSQL
    var description: String?,

    @CreationTimestamp
    var created: Instant?,

    @UpdateTimestamp
    var lastUpdated: Instant?,

    var dueDate: Instant?,

    @Enumerated(EnumType.STRING)
    var priority: EPriority?,

    @Enumerated(EnumType.STRING)
    var state: EState?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity?
) {
    fun toDto() =
        TodoDto(
            id = id,
            title = title,
            description = description,
            created = created,
            dueDate = dueDate,
            lastUpdated = lastUpdated,
            state = state,
            priority = priority
        )

    fun updateFromDto(updateTodoDto: UpdateTodoDto) {
        title = updateTodoDto.title ?: title
        description = updateTodoDto.description ?: description
        dueDate = updateTodoDto.dueDate ?: dueDate
        priority = updateTodoDto.priority ?: priority
        state = updateTodoDto.state ?: state
    }

    companion object {
        fun random() =
            TodoEntity(
                id = null,
                title = "Title ${Random.nextInt(0, 1000)}",
                description = "Desc ${Random.nextInt(0, 1000)}",
                created = null,
                user = null,
                lastUpdated = null,
                dueDate = Instant.now().plus(Random.nextLong(1, 30), ChronoUnit.DAYS),
                priority = EPriority.values().toList().shuffled()[0],
                state = EState.values().toList().shuffled()[0]
            )
    }
}