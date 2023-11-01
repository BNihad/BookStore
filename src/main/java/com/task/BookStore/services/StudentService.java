package com.task.BookStore.services;

import com.task.BookStore.Dao.BookEntity;
import com.task.BookStore.Dao.StudentsEntity;
import com.task.BookStore.repository.BookRepository;
import com.task.BookStore.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BookRepository bookRepository;


    public List<StudentsEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public StudentsEntity createStudent(StudentsEntity student) {
        return studentRepository.save(student);
    }

    public StudentsEntity updateStudent(StudentsEntity student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


    public StudentsEntity addBookToReadingList(Long studentId, Long bookId) {
        StudentsEntity student = studentRepository.findById(studentId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (student != null && book != null) {
            student.getReadingList().add(book);
            studentRepository.save(student);
        }

        return student;
    }


    public List<BookEntity> getReadingList(Long studentId) {
        StudentsEntity student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            throw new NoResultException("Student not found with ID: " + studentId);
        }

        return student.getReadingList();
    }
}
