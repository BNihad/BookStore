package com.task.BookStore.repository;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    BookEntity findByName(String name);


}
