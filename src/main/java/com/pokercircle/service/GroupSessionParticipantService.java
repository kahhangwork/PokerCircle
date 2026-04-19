package com.pokercircle.service;

import java.util.List;
import com.pokercircle.dao.DaoException;
import com.pokercircle.dao.GroupSessionParticipantDao;
import com.pokercircle.domain.GroupSessionParticipant;
import com.pokercircle.domain.GroupSessionParticipantKey;

public class GroupSessionParticipantService {
    private GroupSessionParticipantDao groupSessionParticipantDao;
    
    // CONSTRUCTORS
    public GroupSessionParticipantService() {
        this.groupSessionParticipantDao = new GroupSessionParticipantDao();
    }

    // CRUD Services
    public GroupSessionParticipant addParticipant(GroupSessionParticipant p) {
        try {
            return groupSessionParticipantDao.create(p);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public boolean updateParticipant(GroupSessionParticipant p) {
        try {
            return groupSessionParticipantDao.update(p) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<GroupSessionParticipant> getAllParticipants() {
        try {
            return groupSessionParticipantDao.readAll();
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public GroupSessionParticipant getParticipant(GroupSessionParticipantKey key) {
        try {
            return groupSessionParticipantDao.read(key);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean removeParticipant(GroupSessionParticipantKey key) {
        try {
            return groupSessionParticipantDao.delete(key) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        } 
        return null;
    }

}
