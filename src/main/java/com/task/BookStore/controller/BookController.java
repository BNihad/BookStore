package com.task.BookStore.controller;

import com.task.BookStore.Dao.AuthorEntity;
import com.task.BookStore.Dao.BookEntity;
import com.task.BookStore.Dao.StudentsEntity;
import com.task.BookStore.services.AuthorService;
import com.task.BookStore.services.BookService;
import com.task.BookStore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/books/{bookId}/readers")
    public ResponseEntity<List<StudentsEntity>> getReadersForBook(@PathVariable Long bookId) {
        List<StudentsEntity> readers = bookService.getReadersForBook(bookId);

        if (readers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(readers);
        }
    }


}

