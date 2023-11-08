package com.task.BookStore.controller;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }


    @PostMapping("/books")
    public BookEntity createBook(@RequestBody BookEntity book, Principal principal) {
        String loggedInUsername = principal.getName();
        BookEntity createdBook = bookService.createBook(book, loggedInUsername);
        return createdBook;
    }
    @PutMapping("/{name}")
    public ResponseEntity<BookEntity> updateBookByName(
            @PathVariable String name, @RequestBody BookEntity updatedBook, Principal principal) {
        String loggedInUsername = principal.getName(); // Get the username of the currently logged-in user
        BookEntity updated = bookService.updateBookByName(name, updatedBook, loggedInUsername);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{name}")
    public void deleteBookByName(@PathVariable String name, Principal principal) {
        String loggedInUsername = principal.getName(); // Get the username of the currently logged-in user
        bookService.deleteBookByName(name, loggedInUsername);
    }

    // BookController.java
    @GetMapping("/readers")
    public ResponseEntity<List<StudentsEntity>> getReadersForBook(@RequestParam String bookName) {
        List<StudentsEntity> readers = bookService.getReadersForBook(bookName);

        if (readers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(readers);
        }
    }


}

