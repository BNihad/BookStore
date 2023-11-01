package com.task.BookStore.services;

import com.task.BookStore.Dao.BookEntity;
import com.task.BookStore.Dao.StudentsEntity;
import com.task.BookStore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity createBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public BookEntity updateBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<StudentsEntity> getReadersForBook(Long bookId) {
        // Assuming you have a Book entity with a reference to students who are reading it.
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }

        return book.getReaders();
    }

}
