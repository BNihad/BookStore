package com.task.BookStore.repository;

import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {

    StudentsEntity findByName(String username);

    StudentsEntity deleteByName(String username);

    StudentsEntity findByUser(UserEntity user);


}
