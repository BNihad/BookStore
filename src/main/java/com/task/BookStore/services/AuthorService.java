package com.task.BookStore.services;

import com.task.BookStore.Dao.AuthorEntity;
import com.task.BookStore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    public AuthorEntity createAuthor(AuthorEntity author) {
        return authorRepository.save(author);
    }

    public AuthorEntity updateAuthor(AuthorEntity author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
