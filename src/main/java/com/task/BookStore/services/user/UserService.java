package com.task.BookStore.services.user;


import com.task.BookStore.exception.UserAlreadyExistsException;
import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.models.role.Role;
import com.task.BookStore.models.user.UserEntity;
import com.task.BookStore.repository.AuthorRepository;
import com.task.BookStore.repository.RoleRepository;
import com.task.BookStore.repository.StudentRepository;
import com.task.BookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity registerStudent(UserEntity user) {
        // Ensure the user doesn't exist
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set user roles
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("STUDENT"));
        user.setRoles(roles);

        // Save the user
        user = userRepository.save(user);

        // Create and associate a student entity
        StudentsEntity student = new StudentsEntity();
        student.setUser(user);
        student.setName(user.getUsername()); // Set the username in the student entity
        student.setAge(user.getAge()); // Set the age in the student entity

        studentRepository.save(student);

        return user;
    }

    public UserEntity registerAuthor(UserEntity user) {
        // Ensure the user doesn't exist
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set user roles
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("AUTHOR"));
        user.setRoles(roles);

        // Save the user
        user = userRepository.save(user);

        // Create and associate  author entity
        AuthorEntity author = new AuthorEntity();
        author.setUser(user);
        author.setName(user.getUsername()); // Set the username in the author entity
        author.setAge(user.getAge()); // Set the age in the student entity

        authorRepository.save(author);

        return user;
    }


    public UserEntity registerAdmin(UserEntity user) {
        // Ensures the user doesn't exist
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set user roles
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ADMIN")); // Change to ROLE_AUTHOR for authors
        user.setRoles(roles);

        // Save the user
        return userRepository.save(user);
    }
}

