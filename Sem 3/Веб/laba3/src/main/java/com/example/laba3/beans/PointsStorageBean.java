package com.example.laba3.beans;

import com.example.laba3.model.PointResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


public class PointsStorageBean implements Serializable {

    private List<PointResult> historyList;
    private final SessionFactory sessionFactory;

    public PointsStorageBean() {
        this.historyList = new ArrayList<>();
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<PointResult> getHistoryList() {
        try (var session = sessionFactory.openSession()) {
            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            String httpSessionId = httpSession.getId();
            historyList = session.createQuery("from point_results where sessionId='" + httpSessionId + "'", PointResult.class).getResultList();
            Collections.reverse(historyList);
            return this.historyList;
        }
    }

    public void setHistoryList(List<PointResult> historyList) {
        this.historyList = historyList;
    }

    public void addPoint(PointResult point) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(point);
            session.getTransaction().commit();
            historyList.add(point);
        }
    }

    public void clearHistory() {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            String httpSessionId = httpSession.getId();
            session.createQuery("delete from point_results where sessionId = '" + httpSessionId + "'").executeUpdate();
            session.getTransaction().commit();
            historyList.clear();
        }
    }
}
