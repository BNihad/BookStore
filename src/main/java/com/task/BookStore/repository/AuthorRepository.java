package com.task.BookStore.repository;

import com.task.BookStore.Dao.AuthorEntity;
import com.task.BookStore.Dao.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    // You can define custom query methods here if needed
    Optional<AuthorEntity> findByNameAndAge(String name, int age);


}
