package com.pokercircle.service;

import java.util.List;
import com.pokercircle.dao.DaoException;
import com.pokercircle.dao.PersonalSessionDao;
import com.pokercircle.domain.PersonalSession;

public class PersonalSessionService {
    private PersonalSessionDao personalSessionDao;

    // CONSTRUCTORS
    public PersonalSessionService() {
        this.personalSessionDao = new PersonalSessionDao();
    }

    // CRUD Services
    public PersonalSession createSession(PersonalSession session) {
        try {
            return personalSessionDao.create(session);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public PersonalSession getSession(Integer id) {
        try {
            return personalSessionDao.read(id);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PersonalSession> getAllSessions() {
        try {
            return personalSessionDao.readAll();
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PersonalSession> getSessionsByUser(Integer userId) {
        try {
            return personalSessionDao.readByUser(userId);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateSession(PersonalSession session) {
        try {
            return personalSessionDao.update(session) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteSession(Integer id) {
        try {
            return personalSessionDao.delete(id) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Analytics Methods
    public int getNetResult(PersonalSession session) {
        int cashOut = session.getCashOutCents();
        int buyIn = session.getBuyInCents();
        return cashOut - buyIn;
    }

    public int getTotalNetResult (Integer userId) {
        List<PersonalSession> sessions = getSessionsByUser(userId);
        int total = 0;
        for (PersonalSession session : sessions) {
            total += getNetResult(session);
        }
        return total;
    }

    public double getWinRate(Integer userId) {
        List<PersonalSession> sessions = getSessionsByUser(userId);
        int sessionCount = 0;
        int wins =0;
        for (PersonalSession session : sessions) {
            sessionCount++;
            if (getNetResult(session) > 0) {
                wins++;
            }
        }
        return (double) wins / sessionCount * 100;
    }

    public double getAverageSessionLengthMinutes(Integer userId) {
        List <PersonalSession> sessions = getSessionsByUser(userId);
        long totalMinutes = 0;
        int sessionCount = 0;
        for (PersonalSession session : sessions) {
            sessionCount++;
            long minutes = java.time.Duration.between(session.getSessionStartedAt(), session.getSessionEndedAt()).toMinutes();
            totalMinutes = totalMinutes + minutes;
        }
        return (double) totalMinutes / sessionCount;
    }

    public double getHourlyRateCents(Integer userId) {
        List <PersonalSession> sessions = getSessionsByUser(userId);
        int totalNetResult = getTotalNetResult(userId);

        long totalMinutes = 0;
        for (PersonalSession session : sessions) {
            long minutes = java.time.Duration.between(session.getSessionStartedAt(), session.getSessionEndedAt()).toMinutes();
            totalMinutes = totalMinutes + minutes;
        }

        double totalHours = totalMinutes / 60.0;

        }
        return (double) totalNetResult / totalHours;
    }


}
