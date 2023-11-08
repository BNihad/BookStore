package com.task.BookStore.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.models.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roles;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private StudentsEntity student;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private AuthorEntity author;


}
