package com.task.BookStore.services;

import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.models.subscription.SubscriptionEntity;
import com.task.BookStore.repository.AuthorRepository;
import com.task.BookStore.repository.BookRepository;
import com.task.BookStore.repository.StudentRepository;
import com.task.BookStore.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

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


    public ResponseEntity<?> addBookToReadingList(String loggedInUsername, String bookName) {
        StudentsEntity student = studentRepository.findByName(loggedInUsername);

        if (student == null) {
            return new ResponseEntity<>("Student not found with username: " + loggedInUsername, HttpStatus.NOT_FOUND);
        }

        BookEntity book = bookRepository.findByName(bookName);

        if (book == null) {
            return new ResponseEntity<>("Book not found with Name: " + bookName, HttpStatus.NOT_FOUND);
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


    //TODO


    public ResponseEntity<?> subscribeToAuthor(String loggedInUsername, String authorName) {
        StudentsEntity student = studentRepository.findByName(loggedInUsername);
        AuthorEntity author = authorRepository.findByName(authorName);

        if (student == null) {
            return new ResponseEntity<>("Student not found with username: " + loggedInUsername, HttpStatus.NOT_FOUND);
        }

        if (author == null) {
            return new ResponseEntity<>("Author not found with Name: " + authorName, HttpStatus.NOT_FOUND);
        }

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setStudent(student);
        subscription.setAuthor(author);

        // Save the subscription entity
        subscriptionRepository.save(subscription);

        return new ResponseEntity<>("Subscribed to author: " + author.getName(), HttpStatus.OK);
    }

    public ResponseEntity<?> unsubscribeFromAuthor(String loggedInUsername, String authorName) {
        StudentsEntity student = studentRepository.findByName(loggedInUsername);

        if (student == null) {
            return new ResponseEntity<>("Student not found with Name: " + loggedInUsername, HttpStatus.NOT_FOUND);
        }

        AuthorEntity author = authorRepository.findByName(authorName);

        if (author.equals(null)) {
            return new ResponseEntity<>("Author not found with Name: " + authorName, HttpStatus.NOT_FOUND);
        }

        // Find and delete the subscription entity
        SubscriptionEntity subscription = subscriptionRepository.findByStudentAndAuthor(student, author);
        if (subscription != null) {
            subscriptionRepository.delete(subscription);
        }

        return new ResponseEntity<>("Unsubscribed from author: " + author.getName(), HttpStatus.OK);
    }

    public List<AuthorEntity> getSubscribedAuthors(String loggedInStudentUsername) {
        StudentsEntity student = studentRepository.findByName(loggedInStudentUsername);

        if (student == null) {
            throw new EntityNotFoundException("Student not found with username: " + loggedInStudentUsername);
        }

        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByStudent(student);
        return subscriptions.stream().map(SubscriptionEntity::getAuthor).collect(Collectors.toList());
    }


    public List<StudentsEntity> getSubscribers(Long authorId) {
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + authorId));

        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByAuthor(author);
        return subscriptions.stream().map(SubscriptionEntity::getStudent).collect(Collectors.toList());
    }

}
