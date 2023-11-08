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
@Table(name = "student")
public class StudentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore

    private Long id;
    private String name;
    private int age;

    @ManyToMany
    @JoinTable(
            name = "reading_list",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    @JsonIgnore
    private List<BookEntity> readingList;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
