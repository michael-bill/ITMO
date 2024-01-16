package org.michael.laba4_back.repository;

import jakarta.transaction.Transactional;
import org.michael.laba4_back.model.AuthToken;
import org.michael.laba4_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByToken(String token);
    AuthToken findByUser(User user);
    @Transactional
    void deleteByUserId(Long userId);
}
