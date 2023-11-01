package com.task.BookStore.repository;
import com.task.BookStore.Dao.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {
    List<StudentsEntity> findByAge(int age);
    List<StudentsEntity> findByName(String name);
    List<StudentsEntity> findByAgeBetween(int minAge, int maxAge);
    List<StudentsEntity> findByNameAndAge(String name, int age);
    StudentsEntity findStudentById(Long id);



}
