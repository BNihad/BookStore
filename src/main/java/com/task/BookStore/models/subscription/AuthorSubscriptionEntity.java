package com.task.BookStore.models.subscription;

import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_author_subscription")
public class AuthorSubscriptionEntity {

    // TODO FOR Now we don't use Subscription feature
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
}
