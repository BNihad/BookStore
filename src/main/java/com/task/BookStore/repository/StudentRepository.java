package com.task.BookStore.repository;

import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.models.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {

    StudentsEntity findByName(String username);


}
