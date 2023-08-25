package com.example.prog4.service;

import com.example.prog4.model.exception.ForbiddenException;
import com.example.prog4.repository.employee.SessionRepository;
import com.example.prog4.repository.employee.UserRepository;
import com.example.prog4.repository.employee.entity.Session;
import com.example.prog4.repository.employee.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private static final Integer AUTHENTICATION_DURATION = 3_600;

    public void authenticateUser(User user, String sessionId) {
        Optional<User> currentUser = userRepository.findOneByUsername(user.getUsername());

        if (currentUser.isEmpty()) {
            throw new ForbiddenException("Please verify your credentials.");
        }
        if (!currentUser.get().getPassword().equals(user.getPassword())) {
            throw new ForbiddenException("Please verify your credentials.");
        }
        Session toSave = Session.builder()
            .sessionId(sessionId)
            .user(currentUser.get())
            .timeout(LocalDateTime.now().plusSeconds(AUTHENTICATION_DURATION)).build();
        log.info("Actual session: {}", toSave);
        sessionRepository.save(toSave);
    }

    public void verifySession(String sessionId){
        Optional<Session> session = sessionRepository.findOneBySessionId(sessionId);
        if(session.isEmpty() || session.get().getTimeout().isBefore(LocalDateTime.now())){
            throw new ForbiddenException("Session expired.");
        }
    }

    public void removeSession(String sessionId){
        List<Session> sessions = sessionRepository.findAllBySessionId(sessionId);
        List<Session> sessionList = sessions.stream().peek(session -> session.setTimeout(LocalDateTime.now().minusSeconds(1))).toList();
        sessionRepository.saveAll(sessionList);
    }
}
