package com.task.BookStore.controller;

import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {


    //TODO   future admins should use those crud apis, for now users can already register as student
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentsEntity> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public StudentsEntity createStudent(@RequestBody StudentsEntity student) {
        return studentService.createStudent(student);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public StudentsEntity updateStudent(@PathVariable Long id, @RequestBody StudentsEntity student) {
        student.setId(id);
        return studentService.updateStudent(student);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }


    @PutMapping("/add-book")
    public ResponseEntity<?> addBookToReadingList(@RequestBody String bookName, Principal principal) {
        String loggedInUsername = principal.getName(); // Get the username of the currently logged-in user
        ResponseEntity<?> response = studentService.addBookToReadingList(loggedInUsername, bookName);
        return response;
    }


    @GetMapping("/reading-list")
    public ResponseEntity<List<BookEntity>> getReadingListForStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String loggedInStudentId = userDetails.getUsername(); // Get the student username from the JWT payload

            List<BookEntity> readingList = studentService.getReadingList(loggedInStudentId);
            return new ResponseEntity<>(readingList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Return a 403 Forbidden response for unauthorized access
        }
    }


    @GetMapping("/test")
    public String test() {

        return "test passed";
    }


    @PostMapping("/subscribe-to-author")
    public ResponseEntity<?> subscribeToAuthor(@RequestBody String authorName, Principal principal) {
        String loggedInUsername = principal.getName();
        ResponseEntity<?> response = studentService.subscribeToAuthor(loggedInUsername, authorName);
        return response;
    }

    @PostMapping("/unsubscribe-from-author")
    public ResponseEntity<?> unsubscribeFromAuthor(@RequestBody String authorName, Principal principal) {
        String loggedInUsername = principal.getName();
        ResponseEntity<?> response = studentService.unsubscribeFromAuthor(loggedInUsername, authorName);
        return response;
    }

    @GetMapping("/subscribed-authors")
    public ResponseEntity<List<AuthorEntity>> getSubscribedAuthors(Principal principal) {
        String loggedInUsername = principal.getName();
        List<AuthorEntity> subscribedAuthors = studentService.getSubscribedAuthors(loggedInUsername);
        return ResponseEntity.ok(subscribedAuthors);
    }

}
