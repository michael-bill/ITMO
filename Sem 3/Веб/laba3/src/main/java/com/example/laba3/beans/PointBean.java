package com.example.laba3.beans;

import com.example.laba3.model.PointResult;
import org.primefaces.PrimeFaces;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PointBean implements Serializable {

    private PointsStorageBean pointsStorageBean;

    private double x;
    private double y;
    private double r;

    public void processRequest() {
        boolean ok = true;

        if (r1) r = 1.0;
        else if (r1dot5) r = 1.5;
        else if (r2) r = 2.0;
        else if (r2dot5) r = 2.5;
        else if (r3) r = 3.0;
        else ok = false;

        if (!(-2 <= x && x <= 2)) ok = false;;
        if (!(-5 < y && y < 3)) ok = false;;
        if (!(1 <= r && r <= 3)) ok = false;

        if (!ok) {
            PrimeFaces.current().executeScript("alert('Некорректные данные')");
            return;
        }


        boolean validation = validation(x, y, r);
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String httpSessionId = httpSession.getId();
        pointsStorageBean.addPoint(new PointResult(x, y, r, validation,
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")), httpSessionId));
    }

    private boolean validation(double x, double y, double r) {
        boolean square = (0 <= x) && (x <= r) && (0 <= y) && (y <= r);
        boolean triangle = (x >= 0) && (y <= 0) && (y >= x - r / 2);
        boolean circle = (x <= 0) && (y >= 0) && (x * x + y * y <= r * r / 4);
        return square || triangle || circle;
    }

    private boolean r1;
    private boolean r1dot5;
    private boolean r2 = true;
    private boolean r2dot5;
    private boolean r3;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isR1() {
        return r1;
    }

    public void setR1(boolean r1) {
        this.r1 = r1;
    }

    public boolean isR1dot5() {
        return r1dot5;
    }

    public void setR1dot5(boolean r1dot5) {
        this.r1dot5 = r1dot5;
    }

    public boolean isR2() {
        return r2;
    }

    public void setR2(boolean r2) {
        this.r2 = r2;
    }

    public boolean isR2dot5() {
        return r2dot5;
    }

    public void setR2dot5(boolean r2dot5) {
        this.r2dot5 = r2dot5;
    }

    public boolean isR3() {
        return r3;
    }

    public void setR3(boolean r3) {
        this.r3 = r3;
    }

    public PointsStorageBean getPointsStorageBean() {
        return pointsStorageBean;
    }

    public void setPointsStorageBean(PointsStorageBean pointsStorageBean) {
        this.pointsStorageBean = pointsStorageBean;
    }
}
