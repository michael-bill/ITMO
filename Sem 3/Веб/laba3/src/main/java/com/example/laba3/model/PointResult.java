package com.example.laba3.model;


import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;

@Entity(name="point_results")
@Table(name = "point_results")
public class PointResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "r")
    private double r;
    @Column(name = "validation")
    private boolean validation;
    @Column(name = "cur_time")
    private String currentTime;
    @Column(name = "session_id")
    private String sessionId;

    public PointResult() {}

    public PointResult(double x, double y, double r, boolean validation, String currentTime, String sessionId) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.validation = validation;
        this.currentTime = currentTime;
        this.sessionId = sessionId;
    }

    public double getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(long r) {
        this.r = r;
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
