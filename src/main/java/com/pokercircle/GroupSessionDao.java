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

public class GroupSessionDao implements Dao<Integer, GroupSession> {
    // VARIABLES
    private DataSource datasource;

    // METHODS
    public GroupSessionDao() {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }



    @Override
    public GroupSession create(GroupSession session) throws DaoException {
        // DB already handles timestamps, so we don't need to set them here
        String query = "INSERT INTO grp_session(grp_id, session_type, session_stakes, session_location, session_started_at, session_ended_at, notes) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, session.getGrpId());
            stat.setString(2, session.getSessionType());
            stat.setString(3, session.getSessionStakes());
            stat.setString(4, session.getSessionLocation());
            stat.setTimestamp(5, Timestamp.valueOf(session.getSessionStartedAt()));
            stat.setTimestamp(6, session.getSessionEndedAt() != null ? Timestamp.valueOf(session.getSessionEndedAt()) : null);
            stat.setString(7, session.getNotes());
            stat.executeUpdate();

            ResultSet generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next())
                session.setId(generatedKeys.getInt(1));

        } catch (SQLException ex) {
            throw new DaoException("Error creating group session", ex);
        }

        return session;
    }



    @Override
    public GroupSession read(Integer id) throws DaoException {
        String query = """
            SELECT id, grp_id, session_type, session_stakes, session_location,
                   session_started_at, session_ended_at, notes, created_at, updated_at
            FROM grp_session
            WHERE id = ?
                """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();

            GroupSession session = null;
            if (rs.next()) {
                Timestamp endedAt = rs.getTimestamp("session_ended_at");
                session = new GroupSession(
                    rs.getInt("id"),
                    rs.getInt("grp_id"),
                    rs.getString("session_type"),
                    rs.getString("session_stakes"),
                    rs.getString("session_location"),
                    rs.getTimestamp("session_started_at").toLocalDateTime(),
                    endedAt != null ? endedAt.toLocalDateTime() : null,
                    rs.getString("notes"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }

            return session;

        } catch (SQLException ex) {
            throw new DaoException("Error reading group session with ID: " + id, ex);
        }
    }


    @Override
    public List<GroupSession> readAll() throws DaoException {
        List<GroupSession> sessions = new ArrayList<>();
        String query = """
                SELECT *
                FROM grp_session
                """;

        try (Connection conn = datasource.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {

            while (rs.next()) {
                Timestamp endedAt = rs.getTimestamp("session_ended_at");
                GroupSession session = new GroupSession(
                    rs.getInt("id"),
                    rs.getInt("grp_id"),
                    rs.getString("session_type"),
                    rs.getString("session_stakes"),
                    rs.getString("session_location"),
                    rs.getTimestamp("session_started_at").toLocalDateTime(),
                    endedAt != null ? endedAt.toLocalDateTime() : null,
                    rs.getString("notes"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
                sessions.add(session);
            }

        } catch (SQLException ex) {
            throw new DaoException("Error reading all group sessions", ex);
        }
        return sessions;
    }



    @Override
    public int update(GroupSession session) throws DaoException {
        // DB already handles updated_at timestamp, so we don't need to set it here
        String query = """
            UPDATE grp_session
            SET grp_id = ?, session_type = ?, session_stakes = ?, session_location = ?,
                session_started_at = ?, session_ended_at = ?, notes = ?,
                updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, session.getGrpId());
            stat.setString(2, session.getSessionType());
            stat.setString(3, session.getSessionStakes());
            stat.setString(4, session.getSessionLocation());
            stat.setTimestamp(5, Timestamp.valueOf(session.getSessionStartedAt()));
            stat.setTimestamp(6, session.getSessionEndedAt() != null ? Timestamp.valueOf(session.getSessionEndedAt()) : null);
            stat.setString(7, session.getNotes());
            stat.setInt(8, session.getId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error updating group session with ID: " + session.getId(), ex);
        }
    }


    @Override
    public int delete(Integer id) throws DaoException {
        String query = "DELETE FROM grp_session WHERE id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting group session with ID: " + id, ex);
        }
    }
}
