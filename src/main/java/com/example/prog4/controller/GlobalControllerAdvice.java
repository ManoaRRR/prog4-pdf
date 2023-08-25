package com.example.prog4.controller;

import com.example.prog4.model.exception.ForbiddenException;
import com.example.prog4.model.exception.ServerException;
import com.example.prog4.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

@AllArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {
    private AuthService authService;

    @ModelAttribute
    public void authentication(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!request.getRequestURI().contains("login")) {
            try {
                authService.verifySession(httpSession.getId());
            } catch (ForbiddenException e) {
                response.sendRedirect("/login");
            }
        }
    }

    @ExceptionHandler(ServerException.class)
    public String handleBadRequestException(ServerException e, Model model) {
        model.addAttribute("exception", e);
        return "error";
    }
}
