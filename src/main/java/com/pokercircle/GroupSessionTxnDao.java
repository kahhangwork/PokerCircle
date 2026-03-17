package com.pokercircle;
import javax.sql.DataSource;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

// Note: Dao key type is Long (capital L) to match the BIGINT id column
public class GroupSessionTxnDao implements Dao<Long, GroupSessionTxn> {
    // VARIABLES
    private DataSource datasource;

    // METHODS
    public GroupSessionTxnDao() {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }



    @Override
    public GroupSessionTxn create(GroupSessionTxn txn) throws DaoException {
        String query = "INSERT INTO grp_session_txn(session_id, from_member_id, to_member_id, txn_type, amount_cents, txn_at, notes) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, txn.getSessionId());
            // setObject handles null correctly for Integer fields — setInt cannot handle null
            stat.setObject(2, txn.getFromMemberId());
            stat.setObject(3, txn.getToMemberId());
            stat.setString(4, txn.getTxnType());
            stat.setInt(5, txn.getAmountCents());
            stat.setTimestamp(6, Timestamp.valueOf(txn.getTxnAt()));
            stat.setString(7, txn.getNotes());
            stat.executeUpdate();

            ResultSet generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next())
                txn.setId(generatedKeys.getLong(1));

        } catch (SQLException ex) {
            throw new DaoException("Error creating group session transaction", ex);
        }

        return txn;
    }



    @Override
    public GroupSessionTxn read(Long id) throws DaoException {
        String query = """
            SELECT id, session_id, from_member_id, to_member_id, txn_type, amount_cents, txn_at, notes
            FROM grp_session_txn
            WHERE id = ?
                """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setLong(1, id);
            ResultSet rs = stat.executeQuery();

            GroupSessionTxn txn = null;
            if (rs.next()) {
                // getObject returns null safely for nullable integer columns
                Integer fromMemberId = (Integer) rs.getObject("from_member_id");
                Integer toMemberId = (Integer) rs.getObject("to_member_id");
                txn = new GroupSessionTxn(
                    rs.getLong("id"),
                    rs.getInt("session_id"),
                    fromMemberId,
                    toMemberId,
                    rs.getString("txn_type"),
                    rs.getInt("amount_cents"),
                    rs.getTimestamp("txn_at").toLocalDateTime(),
                    rs.getString("notes")
                );
            }

            return txn;

        } catch (SQLException ex) {
            throw new DaoException("Error reading group session transaction with ID: " + id, ex);
        }
    }


    @Override
    public List<GroupSessionTxn> readAll() throws DaoException {
        List<GroupSessionTxn> txns = new ArrayList<>();
        String query = """
                SELECT *
                FROM grp_session_txn
                """;

        try (Connection conn = datasource.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {

            while (rs.next()) {
                Integer fromMemberId = (Integer) rs.getObject("from_member_id");
                Integer toMemberId = (Integer) rs.getObject("to_member_id");
                GroupSessionTxn txn = new GroupSessionTxn(
                    rs.getLong("id"),
                    rs.getInt("session_id"),
                    fromMemberId,
                    toMemberId,
                    rs.getString("txn_type"),
                    rs.getInt("amount_cents"),
                    rs.getTimestamp("txn_at").toLocalDateTime(),
                    rs.getString("notes")
                );
                txns.add(txn);
            }

        } catch (SQLException ex) {
            throw new DaoException("Error reading all group session transactions", ex);
        }
        return txns;
    }



    @Override
    public int update(GroupSessionTxn txn) throws DaoException {
        String query = """
            UPDATE grp_session_txn
            SET session_id = ?, from_member_id = ?, to_member_id = ?,
                txn_type = ?, amount_cents = ?, txn_at = ?, notes = ?
            WHERE id = ?
            """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, txn.getSessionId());
            stat.setObject(2, txn.getFromMemberId());
            stat.setObject(3, txn.getToMemberId());
            stat.setString(4, txn.getTxnType());
            stat.setInt(5, txn.getAmountCents());
            stat.setTimestamp(6, Timestamp.valueOf(txn.getTxnAt()));
            stat.setString(7, txn.getNotes());
            stat.setLong(8, txn.getId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error updating group session transaction with ID: " + txn.getId(), ex);
        }
    }


    @Override
    public int delete(Long id) throws DaoException {
        String query = "DELETE FROM grp_session_txn WHERE id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setLong(1, id);

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting group session transaction with ID: " + id, ex);
        }
    }
}
