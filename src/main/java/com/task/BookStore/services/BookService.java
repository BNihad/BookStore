package com.task.BookStore.services;

import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.BookEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.repository.AuthorRepository;
import com.task.BookStore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private EmailNotificationService emailNotificationService;
    @Autowired
    private StudentService studentService;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }


    public BookEntity createBook(BookEntity book, String loggedInUsername) {
        AuthorEntity author = authorRepository.findByName(loggedInUsername);

        if (author == null) {
            throw new EntityNotFoundException("Author not found with username: " + loggedInUsername);
        }

        book.setAuthor(author);
        BookEntity newBook = bookRepository.save(book);

        // Notify subscribers about the new book
        List<StudentsEntity> subscribers = studentService.getSubscribers(author.getId());
        for (StudentsEntity subscriber : subscribers) {
            String emailSubject = "New Book Notification";
            String emailContent = "Author " + author.getName() + " has created a new book: " + newBook.getName();
            emailNotificationService.sendEmail(subscriber.getUser().getEmail(), emailSubject, emailContent);
        }

        return newBook;
    }

    public BookEntity updateBookByName(String name, BookEntity updatedBook, String loggedInUsername) {
        BookEntity existingBooks = bookRepository.findByName(name);

        if (existingBooks == null) {
            throw new EntityNotFoundException("Book not found with Name: " + name);
        }


        BookEntity existingBook = existingBooks;

        // Check if the currently logged-in user is the author of the book
        if (!existingBook.getAuthor().getName().equals(loggedInUsername)) {
            throw new AccessDeniedException("You don't have permission to update this book.");
        }

        // Update the book
        existingBook.setName(updatedBook.getName());
        return bookRepository.save(existingBook);
    }


    public void deleteBookByName(String bookName, String loggedInUsername) {
        BookEntity existingBook = bookRepository.findByName(bookName);
        if (existingBook == null) {
            throw new EntityNotFoundException("Book not found with Name: " + bookName);
        }

        // Check if the currently logged-in user is the author of the book
        if (!existingBook.getAuthor().getName().equals(loggedInUsername)) {
            throw new AccessDeniedException("You don't have permission to delete this book.");
        }

        bookRepository.delete(existingBook);
    }


    @Transactional
    public List<StudentsEntity> getReadersForBook(String bookName) {
        BookEntity book = bookRepository.findByName(bookName);

        if (book == null) {
            throw new EntityNotFoundException("Book not found with Name: " + bookName);
        }

        return book.getReaders();
    }


}
