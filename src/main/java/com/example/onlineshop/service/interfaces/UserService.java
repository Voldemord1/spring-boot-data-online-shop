package com.example.onlineshop.service.interfaces;

import com.example.onlineshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(String email, String password, String role);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    void updateUser(Long userId, String email, String password, String role);

    void deleteUser(User user);

    boolean isUserExist(String email);
}
