package com.task.BookStore.services;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.repository.BookRepository;
import com.task.BookStore.repository.StudentRepository;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public ResponseEntity<?> addBookToReadingList(Long studentId, Long bookId, String loggedInUsername) {
        StudentsEntity student = studentRepository.findById(studentId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (student != null && book != null && student.getName().equals(loggedInUsername)) {
            student.getReadingList().add(book);
            studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You are not authorized to modify this student's reading list.");
        }
    }



    public List<BookEntity> getReadingList(Long studentId) {
        StudentsEntity student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            throw new NoResultException("Student not found with ID: " + studentId);
        }

        return student.getReadingList();
    }
}
