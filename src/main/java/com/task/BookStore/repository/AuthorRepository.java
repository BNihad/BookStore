package com.task.BookStore.repository;

import com.task.BookStore.models.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByNameAndAge(String name, int age);

    AuthorEntity findByName(String name);


}
