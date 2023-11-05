package com.task.BookStore.services;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.repository.BookRepository;
import com.task.BookStore.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    public ResponseEntity<?> addBookToReadingList(String loggedInUsername, Long bookId) {
        StudentsEntity student = studentRepository.findByName(loggedInUsername);

        if (student == null) {
            return new ResponseEntity<>("Student not found with username: " + loggedInUsername, HttpStatus.NOT_FOUND);
        }

        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            return new ResponseEntity<>("Book not found with ID: " + bookId, HttpStatus.NOT_FOUND);
        }

        // Add the book to the student's reading list
        student.getReadingList().add(book);

        // Save the updated student entity
        studentRepository.save(student);

        return new ResponseEntity<>("Book added to reading list for user: " + loggedInUsername, HttpStatus.OK);
    }




    public List<BookEntity> getReadingList(String loggedInStudentUsername) {
        StudentsEntity student = studentRepository.findByName(loggedInStudentUsername);

        if (student == null) {
            throw new EntityNotFoundException("Student not found with username: " + loggedInStudentUsername);
        }

        return student.getReadingList();
    }



}
