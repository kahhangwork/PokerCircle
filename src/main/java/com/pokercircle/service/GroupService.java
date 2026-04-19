package com.pokercircle.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.pokercircle.domain.Group;
import com.pokercircle.dao.GroupDao;
import com.pokercircle.dao.DaoException;
import com.pokercircle.domain.GroupMember;
import com.pokercircle.domain.GroupSessionTxn;
import com.pokercircle.dao.GroupMemberDao;
import com.pokercircle.dao.GroupSessionDao;
import com.pokercircle.dao.GroupSessionTxnDa


public class GroupService {
    private GroupDao groupDao;
    private GroupMemberDao groupMemberDao;
    private GroupSessionDao groupSessionDao;
    private GroupSessionTxnDao groupSessionTxnDao;

    // CONSTRUCTORS
    public GroupService() {
        this.groupDao = new GroupDao();
        this.groupMemberDao = new GroupMemberDao();
        this.groupSessionDao = new GroupSessionDao();
        this.groupSessionTxnDao = new GroupSessionTxnDao();
    }

    // CRUD Services
    public Group createGroup(Group group) {
        try {
            return groupDao.create(group);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return group;

    }

    public Group getGroup(Integer id) {
        try {
            return groupDao.read(id);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateGroup(Group group) {
        try {
            return groupDao.update(group) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteGroup(Integer id) {
        try {
            return groupDao.delete(id) > 0;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    // assign members to group
    public GroupMember assignMemberToGroup(int requestingUserId, Group group, int newUserId) {
        // only group admin (created_by) can assign members to group
        if (requestingUserId != group.getCreatedBy()) {
            System.out.println("Only group admin can assign members to a group.");
            return null;
        }

        GroupMember newMember = new GroupMember(group.getId(), newUserId);
        try{
            return groupMemberDao.create(newMember);
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    // Get Leaderboard Method
    public Map<Integer, Integer> getLeaderBoard(Integer groupId) {
        try { 
            List<GroupSession> sessions = groupSessionDao.readByGroup(groupId);
            Map<Integer, Integer> leaderboard = new HashMap<>();

            for (GroupSession session : sessions) {
                List<GroupSessionTxn> txns = groupSessionTxnDao.readBySession(session.getId());
                for (GroupSessionTxn txn : txns) {
                    if (txn.getTxnType().equals("CASH_OUT")) {
                        int memberId = txn.getFromMemberId();
                        if (!leaderboard.containsKey(memberId)) {
                            leaderboard.put(memberId, 0);
                        }
                        leaderboard.put(memberId, leaderboard.get(memberId) + txn.getAmountCents());
                    } 
                    else if (txn.getTxnType().equals("BUY_IN")) {
                        int memberId = txn.getToMemberId();
                        if (!leaderboard.containsKey(memberId)) {
                            leaderboard.put(memberId, 0);
                        }
                        leaderboard.put(memberId, leaderboard.get(memberId) - txn.getAmountCents());
                    }
                    else if (txn.getTxnType().equals("TRANSFER")) {
                        int fromId = txn.getFromMemberId();
                        int toId = txn.getToMemberId();
                        if (!leaderboard.containsKey(fromId)) {
                            leaderboard.put(fromId, 0);
                        }
                        if (!leaderboard.containsKey(toId)) {
                            leaderboard.put(toId, 0);
                        }
                        leaderboard.put(fromId, leaderboard.get(fromId) - txn.getAmountCents());
                        leaderboard.put(toId, leaderboard.get(toId) + txn.getAmountCents());
                    }       
                }
            }
        return leaderboard;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
    return null;
    }






    
}
