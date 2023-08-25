package com.example.prog4.repository.employee;

import com.example.prog4.repository.employee.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    @Query(value = "select * from session s where s.session_id = :sessionId order by s.timeout desc limit 1", nativeQuery = true)
    Optional<Session> findOneBySessionId(@Param("sessionId") String sessionId);

    @Query(value = "select * from session s where s.session_id = :sessionId", nativeQuery = true)
    List<Session> findAllBySessionId(@Param("sessionId") String sessionId);
}
