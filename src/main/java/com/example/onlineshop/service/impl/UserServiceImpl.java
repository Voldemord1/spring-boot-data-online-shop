package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.User;
import com.example.onlineshop.repository.UserJpaRepository;
import com.example.onlineshop.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserJpaRepository userJpaRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void addUser(String email, String password, String role) {
        userJpaRepository.save(new User(email, bCryptPasswordEncoder.encode(password), role));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userJpaRepository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void updateUser(Long userId, String email, String password, String role) {
        Optional<User> userById = userJpaRepository.findById(userId);
        if (userById.isPresent()) {
            userById.get().setEmail(email);
            userById.get().setPassword(bCryptPasswordEncoder.encode(password));
            userById.get().setRole(role);
        } else {
            logger.error("User with ID: " + userId + " not found.");
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userJpaRepository.delete(user);
    }

    @Override
    public boolean isUserExist(String email) {
        return getUserByEmail(email).isPresent();
    }
}
