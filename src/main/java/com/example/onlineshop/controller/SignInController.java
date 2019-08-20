package com.example.onlineshop.controller;

import com.example.onlineshop.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class SignInController {

    @GetMapping(path = "/")
    public String login(@AuthenticationPrincipal User user) {
        if (Objects.isNull(user)) {
            return "redirect:/login";
        } else if (user.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/users";
        } else {
            return "redirect:/user/products";
        }
    }
}
