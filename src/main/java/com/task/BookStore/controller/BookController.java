package com.task.BookStore.controller;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public BookEntity createBook(@RequestBody BookEntity book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity book) {
        book.setId(id);
        return bookService.updateBook(book);
    }


    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // BookController.java
    @GetMapping("/{bookId}/readers")
    public ResponseEntity<List<StudentsEntity>> getReadersForBook(@PathVariable Long bookId) {
        List<StudentsEntity> readers = bookService.getReadersForBook(bookId);

        if (readers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(readers);
        }
    }


}

