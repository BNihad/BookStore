package com.task.BookStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.BookStore.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String name;
    private int age;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<BookEntity> authoredBooks;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
