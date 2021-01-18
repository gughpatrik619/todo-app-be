package com.example.todoapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    var title: String?,

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
    @JsonIgnore
    var user: User?
) {
    companion object {
        fun random() =
            Todo(
                id = null,
                title = "Title ${Random.nextInt(0, 1000)}",
                description = "Desc ${Random.nextInt(0, 1000)}",
                created = null,
                user = null,
                lastUpdated = null,
                dueDate = Instant.now().plus(Random.nextLong(1, 10), ChronoUnit.DAYS),
                priority = EPriority.values().toList().shuffled()[0],
                state = EState.values().toList().shuffled()[0]
            )
    }
}