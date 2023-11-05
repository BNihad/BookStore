package com.task.BookStore.services;

import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }

        return book.getReaders();
    }

}
