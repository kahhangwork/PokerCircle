package com.pokercircle.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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


    // Validation of Session Txn
    public int getNetTotal(Integer sessionId) {
        try {
            List<GroupSessionTxn> txns = groupSessionTxnDao.readBySession(sessionId);
            int netTotal = 0;
            for (GroupSessionTxn txn : txns) {
                if(txn.getTxnType().equals("BUY_IN")) {
                    netTotal -= txn.getAmountCents();
                } else if (txn.getTxnType().equals("CASH_OUT")) {
                    netTotal += txn.getAmountCents();
                }
            }
            return netTotal;
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return 404; // Return non-zero value to indicate error in validation
    }

    // Settlement Logic
    public List<String> calculateSettlement(Integer sessionId) {
        List<String> settlements = new ArrayList<>();
        try {
            List<GroupSessionTxn> txns = groupSessionTxnDao.readBySession(sessionId);
            Map<Integer, Integer> netPositions = new HashMap<>();

            // Step 1: Calculate each player's net position
            for (GroupSessionTxn txn : txns) {
                if (txn.getTxnType().equals("BUY_IN")) {
                    int memberId = txn.getToMemberId();
                    netPositions.put(memberId, netPositions.getOrDefault(memberId, 0) - txn.getAmountCents());
                } else if (txn.getTxnType().equals("CASH_OUT")) {
                    int memberId = txn.getFromMemberId();
                    netPositions.put(memberId, netPositions.getOrDefault(memberId, 0) + txn.getAmountCents());
                } else if (txn.getTxnType().equals("TRANSFER")) {
                    int from = txn.getFromMemberId();
                    int to = txn.getToMemberId();
                    netPositions.put(from, netPositions.getOrDefault(from, 0) - txn.getAmountCents());
                    netPositions.put(to, netPositions.getOrDefault(to, 0) + txn.getAmountCents());
                }
            }

            // Step 2: Split into winners and losers
            List<int[]> winners = new ArrayList<>(); // [memberId, amount]
            List<int[]> losers = new ArrayList<>();  // [memberId, amount]

            for (Map.Entry<Integer, Integer> entry : netPositions.entrySet()) {
                if (entry.getValue() > 0) {
                    winners.add(new int[]{entry.getKey(), entry.getValue()});
                } else if (entry.getValue() < 0) {
                    losers.add(new int[]{entry.getKey(), -entry.getValue()}); // Store as positive for easier calculation
                }
            }

            // Step 3: match losers to winners for settlement
            int i = 0, j = 0;
            while (i < losers.size() && j < winners.size()) {
                int loser = losers.get(i)[0];
                int winner = winners.get(j)[0];
                int amount = Math.min(losers.get(i)[1], winners.get(j)[1]);

                settlements.add("Member " + loser + " pays Member " + winner + " " + amount + " cents");

                losers.get(i)[1] -+amount;
                winners.get(j)[1] -= amount;

                if (losers.get(i)[1] == 0) i++;
                if (winners.get(j)[1] == 0) j++;
            }
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return settlements;
    }



}
