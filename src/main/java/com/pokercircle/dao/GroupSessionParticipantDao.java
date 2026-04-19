package com.pokercircle.dao;
import javax.sql.DataSource;

import com.pokercircle.domain.GroupSessionParticipant;
import com.pokercircle.domain.GroupSessionParticipantKey;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class GroupSessionParticipantDao implements Dao<GroupSessionParticipantKey, GroupSessionParticipant> {
    // VARIABLES
    private DataSource datasource;

    // METHODS
    public GroupSessionParticipantDao() {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }



    @Override
    public GroupSessionParticipant create(GroupSessionParticipant participant) throws DaoException {
        // No RETURN_GENERATED_KEYS — this table has no auto-increment id
        String query = "INSERT INTO grp_session_participant(session_id, member_id, joined_at, left_at) VALUES(?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, participant.getSessionId());
            stat.setInt(2, participant.getMemberId());
            stat.setTimestamp(3, participant.getJoinedAt() != null ? Timestamp.valueOf(participant.getJoinedAt()) : null);
            stat.setTimestamp(4, participant.getLeftAt() != null ? Timestamp.valueOf(participant.getLeftAt()) : null);
            stat.executeUpdate();

        } catch (SQLException ex) {
            throw new DaoException("Error creating group session participant", ex);
        }

        return participant;
    }



    @Override
    public GroupSessionParticipant read(GroupSessionParticipantKey key) throws DaoException {
        String query = """
            SELECT session_id, member_id, joined_at, left_at
            FROM grp_session_participant
            WHERE session_id = ? AND member_id = ?
                """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, key.getSessionId());
            stat.setInt(2, key.getMemberId());
            ResultSet rs = stat.executeQuery();

            GroupSessionParticipant participant = null;
            if (rs.next()) {
                Timestamp joinedAt = rs.getTimestamp("joined_at");
                Timestamp leftAt = rs.getTimestamp("left_at");
                participant = new GroupSessionParticipant(
                    rs.getInt("session_id"),
                    rs.getInt("member_id"),
                    joinedAt != null ? joinedAt.toLocalDateTime() : null,
                    leftAt != null ? leftAt.toLocalDateTime() : null
                );
            }

            return participant;

        } catch (SQLException ex) {
            throw new DaoException("Error reading participant for session " + key.getSessionId() + ", member " + key.getMemberId(), ex);
        }
    }


    @Override
    public List<GroupSessionParticipant> readAll() throws DaoException {
        List<GroupSessionParticipant> participants = new ArrayList<>();
        String query = """
                SELECT *
                FROM grp_session_participant
                """;

        try (Connection conn = datasource.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {

            while (rs.next()) {
                Timestamp joinedAt = rs.getTimestamp("joined_at");
                Timestamp leftAt = rs.getTimestamp("left_at");
                GroupSessionParticipant participant = new GroupSessionParticipant(
                    rs.getInt("session_id"),
                    rs.getInt("member_id"),
                    joinedAt != null ? joinedAt.toLocalDateTime() : null,
                    leftAt != null ? leftAt.toLocalDateTime() : null
                );
                participants.add(participant);
            }

        } catch (SQLException ex) {
            throw new DaoException("Error reading all group session participants", ex);
        }
        return participants;
    }



    @Override
    public int update(GroupSessionParticipant participant) throws DaoException {
        String query = """
            UPDATE grp_session_participant
            SET joined_at = ?, left_at = ?
            WHERE session_id = ? AND member_id = ?
            """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setTimestamp(1, participant.getJoinedAt() != null ? Timestamp.valueOf(participant.getJoinedAt()) : null);
            stat.setTimestamp(2, participant.getLeftAt() != null ? Timestamp.valueOf(participant.getLeftAt()) : null);
            stat.setInt(3, participant.getSessionId());
            stat.setInt(4, participant.getMemberId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error updating participant for session " + participant.getSessionId() + ", member " + participant.getMemberId(), ex);
        }
    }


    @Override
    public int delete(GroupSessionParticipantKey key) throws DaoException {
        String query = "DELETE FROM grp_session_participant WHERE session_id = ? AND member_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, key.getSessionId());
            stat.setInt(2, key.getMemberId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting participant for session " + key.getSessionId() + ", member " + key.getMemberId(), ex);
        }
    }
}
