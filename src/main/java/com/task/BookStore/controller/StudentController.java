package com.task.BookStore.controller;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

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

    @PutMapping("/{id}")
    public StudentsEntity updateStudent(@PathVariable Long id, @RequestBody StudentsEntity student) {
        student.setId(id);
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }


    @PutMapping("/{studentId}/add-book/{bookId}")
    public ResponseEntity<?> addBookToReadingList(@PathVariable Long studentId, @PathVariable Long bookId, Principal principal) {
        String loggedInUsername = principal.getName(); // Get the username of the currently logged-in user
        ResponseEntity<?> student = studentService.addBookToReadingList(studentId, bookId, loggedInUsername);
        return student;
    }



    @GetMapping("/reading-list")
    public ResponseEntity<List<BookEntity>> getReadingListForStudent(@RequestParam Long studentId) {
        List<BookEntity> readingList = studentService.getReadingList(studentId);
        return new ResponseEntity<>(readingList, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {

        return "test passed";
    }
}
