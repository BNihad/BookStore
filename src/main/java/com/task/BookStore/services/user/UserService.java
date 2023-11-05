package com.task.BookStore.services.user;


import com.task.BookStore.models.role.Role;
import com.task.BookStore.models.user.UserEntity;
import com.task.BookStore.exception.UserAlreadyExistsException;
import com.task.BookStore.repository.RoleRepository;
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
    PasswordEncoder passwordEncoder;

    public UserEntity registerUser(UserEntity user) {
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
        return userRepository.save(user);
    }
    public UserEntity registerAuthor(UserEntity user) {
        // Ensures the user doesn't exist
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set user roles
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("AUTHOR")); // Change to ROLE_AUTHOR for authors
        user.setRoles(roles);

        // Save the user
        return userRepository.save(user);
    }
}

