package com.pokercircle.service;

import java.util.List;
import com.pokercircle.dao.DaoException;
import com.pokercircle.dao.GroupSessionTxnDao;
import com.pokercircle.domain.GroupSessionTxn;

public class GroupSessionTxnService {
    private GroupSessionTxnDao groupSessionTxnDao;
    

    // Constructors
    public GroupSessionTxnService() {
        this.groupSessionTxnDao = new GroupSessionTxnDao();
    }

    // CRUD services
    public GroupSessionTxn createTxn(GroupSessionTxn txn) {
        try {
            return groupSessionTxnDao.create(txn);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public GroupSessionTxn getTxn(Long id) {
        try {
            return groupSessionTxnDao.read(id);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<GroupSessionTxn> getTxnsBySession(Integer sessionId) {
        try {
            return groupSessionTxnDao.readBySession(sessionId);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateTxn(GroupSessionTxn txn) {
        try {
            return groupSessionTxnDao.update(txn) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteTxn(Long id) {
        try {
            return groupSessionTxnDao.delete(id) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }



}
