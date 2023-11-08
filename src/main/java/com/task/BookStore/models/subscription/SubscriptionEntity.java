package com.task.BookStore.models.subscription;


import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.StudentsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "subscription")
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private StudentsEntity student;

    @ManyToOne
    private AuthorEntity author;
}
