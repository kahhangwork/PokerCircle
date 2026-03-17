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

public class PersonalSessionDao implements Dao<Integer, PersonalSession> {
    // VARIABLES
    private DataSource datasource;

    // METHODS
    public PersonalSessionDao() {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }



    @Override
    public PersonalSession create(PersonalSession session) throws DaoException {
        // DB already handles timestamps, so we don't need to set them here
        String query = "INSERT INTO personal_session(usr_id, session_type, session_stakes, session_location, session_started_at, session_ended_at, buy_in_cents, cash_out_cents, notes) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, session.getUserId());
            stat.setString(2, session.getSessionType());
            stat.setString(3, session.getSessionStakes());
            stat.setString(4, session.getSessionLocation());
            stat.setTimestamp(5, Timestamp.valueOf(session.getSessionStartedAt()));
            stat.setTimestamp(6, session.getSessionEndedAt() != null ? Timestamp.valueOf(session.getSessionEndedAt()) : null);
            stat.setInt(7, session.getBuyInCents());
            stat.setInt(8, session.getCashOutCents());
            stat.setString(9, session.getNotes());
            stat.executeUpdate();

            ResultSet generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next())
                session.setId(generatedKeys.getInt(1));

        } catch (SQLException ex) {
            throw new DaoException("Error creating personal session", ex);
        }

        return session;
    }



    @Override
    public PersonalSession read(Integer id) throws DaoException {
        String query = """
            SELECT session_id, usr_id, session_type, session_stakes, session_location,
                   session_started_at, session_ended_at, buy_in_cents, cash_out_cents, notes, created_at, updated_at
            FROM personal_session
            WHERE session_id = ?
                """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();

            PersonalSession session = null;
            if (rs.next()) {
                Timestamp endedAt = rs.getTimestamp("session_ended_at");
                session = new PersonalSession(
                    rs.getInt("session_id"),
                    rs.getInt("usr_id"),
                    rs.getString("session_type"),
                    rs.getString("session_stakes"),
                    rs.getString("session_location"),
                    rs.getTimestamp("session_started_at").toLocalDateTime(),
                    endedAt != null ? endedAt.toLocalDateTime() : null,
                    rs.getInt("buy_in_cents"),
                    rs.getInt("cash_out_cents"),
                    rs.getString("notes"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }

            return session;

        } catch (SQLException ex) {
            throw new DaoException("Error reading personal session with ID: " + id, ex);
        }
    }


    @Override
    public List<PersonalSession> readAll() throws DaoException {
        List<PersonalSession> sessions = new ArrayList<>();
        String query = """
                SELECT *
                FROM personal_session
                """;

        try (Connection conn = datasource.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {

            while (rs.next()) {
                Timestamp endedAt = rs.getTimestamp("session_ended_at");
                PersonalSession session = new PersonalSession(
                    rs.getInt("session_id"),
                    rs.getInt("usr_id"),
                    rs.getString("session_type"),
                    rs.getString("session_stakes"),
                    rs.getString("session_location"),
                    rs.getTimestamp("session_started_at").toLocalDateTime(),
                    endedAt != null ? endedAt.toLocalDateTime() : null,
                    rs.getInt("buy_in_cents"),
                    rs.getInt("cash_out_cents"),
                    rs.getString("notes"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
                sessions.add(session);
            }

        } catch (SQLException ex) {
            throw new DaoException("Error reading all personal sessions", ex);
        }
        return sessions;
    }



    @Override
    public int update(PersonalSession session) throws DaoException {
        // DB already handles updated_at timestamp, so we don't need to set it here
        String query = """
            UPDATE personal_session
            SET usr_id = ?, session_type = ?, session_stakes = ?, session_location = ?,
                session_started_at = ?, session_ended_at = ?, buy_in_cents = ?, cash_out_cents = ?, notes = ?,
                updated_at = CURRENT_TIMESTAMP
            WHERE session_id = ?
            """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, session.getUserId());
            stat.setString(2, session.getSessionType());
            stat.setString(3, session.getSessionStakes());
            stat.setString(4, session.getSessionLocation());
            stat.setTimestamp(5, Timestamp.valueOf(session.getSessionStartedAt()));
            stat.setTimestamp(6, session.getSessionEndedAt() != null ? Timestamp.valueOf(session.getSessionEndedAt()) : null);
            stat.setInt(7, session.getBuyInCents());
            stat.setInt(8, session.getCashOutCents());
            stat.setString(9, session.getNotes());
            stat.setInt(10, session.getId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error updating personal session", ex);
        }
    }


    @Override
    public int delete(Integer id) throws DaoException {
        String query = "DELETE FROM personal_session WHERE session_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting personal session", ex);
        }
    }
}