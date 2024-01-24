package org.michael.laba4_back.repository;

import jakarta.transaction.Transactional;
import org.michael.laba4_back.model.Point;
import org.michael.laba4_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("SELECT p FROM Point p WHERE p.user.id = ?1 ORDER BY p.time DESC")
    List<Point> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Point p WHERE p.user.id = ?1")
    void deleteByUserId(Long userId);
}
