package com.example.prog4.controller;

import com.example.prog4.repository.employee.entity.User;
import com.example.prog4.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/server/auth")
public class AuthenticationController {
    private AuthService authService;

    @GetMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        authService.authenticateUser(user, session.getId());
        return "redirect:/employee/list";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        authService.removeSession(session.getId());
        return "redirect:/login";
    }
}

