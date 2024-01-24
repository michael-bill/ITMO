package org.michael.laba4_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double x;
    private double y;
    private double r;
    private boolean hit;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static boolean isValid(double x, double y, double r) {
        boolean xOk = -4 <= x && x <= 4;
        boolean yOk = -3 <= y && y <= 5 && y != -3 && y != 5;
        boolean rOk = 0.1 <= r && r <= 4;
        return xOk && yOk && rOk;
    }

    public static boolean isHit(double x, double y, double r) {
        boolean circle = (x <= 0) && (y >= 0) && (x * x + y * y <= r * r);
        boolean square = (0 <= x) && (x <= r) && (0 <= y) && (y <= r);
        boolean triangle = (x >= 0) && (y <= 0) && (y >= x - r);
        return square || triangle || circle;
    }

    public Point(double x, double y, double r, boolean b, LocalDateTime now, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = b;
        this.time = now;
        this.user = user;
    }
}
