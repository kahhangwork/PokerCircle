package com.pokercircle.service;

import java.util.List;
import com.pokercircle.dao.DaoException;
import com.pokercircle.dao.GroupSessionDao;
import com.pokercircle.domain.GroupSession;

public class GroupSessionService {
    private GroupSessionDao groupSessionDao;

    // CONSTRUCTORS
    public GroupSessionService() {
        this.groupSessionDao = new GroupSessionDao();
    }
    
    // CRUD Services
    public GroupSession createSession(GroupSession session) {
        try {
            return groupSessionDao.create(session);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public GroupSession getSession(Integer id) {
        try {
            return groupSessionDao.read(id);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<GroupSession> getAllSessions() {
        try {
            return groupSessionDao.readAll();
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<GroupSession> getAllSessionsByGroup(Integer grpId) {
        try {
            return groupSessionDao.readByGroup(grpId);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateSession(GroupSession session) {
        try {
            return groupSessionDao.update(session) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteSession(Integer id) {
        try {
            return groupSessionDao.delete(id) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
