package com.task.BookStore.controller;

import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<AuthorEntity> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public AuthorEntity createAuthor(@RequestBody AuthorEntity author) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorEntity updateAuthor(@PathVariable Long id, @RequestBody AuthorEntity author) {
        author.setId(id);
        return authorService.updateAuthor(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
