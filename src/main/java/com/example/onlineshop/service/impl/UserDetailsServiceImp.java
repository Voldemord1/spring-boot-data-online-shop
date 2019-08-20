package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.User;
import com.example.onlineshop.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    private User findUserByEmail(String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            if (email.equalsIgnoreCase(user.get().getEmail())) {
                return user.get();
            }
        }
        return null;
    }
}
