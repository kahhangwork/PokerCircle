package com.pokercircle.dao;
import javax.sql.DataSource;

import com.pokercircle.domain.GroupMember;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class GroupMemberDao implements Dao<Integer, GroupMember> {
    // VARIABLES
    private DataSource datasource;

    // METHODS
    public GroupMemberDao() {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }



    @Override
    public GroupMember create(GroupMember member) throws DaoException {
        // DB handles joined_at via DEFAULT CURRENT_TIMESTAMP
        String query = "INSERT INTO grp_member(grp_id, usr_id) VALUES(?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stat.setInt(1, member.getGrpId());
            stat.setInt(2, member.getUsrId());
            stat.executeUpdate();

            ResultSet generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next())
                member.setId(generatedKeys.getInt(1));

        } catch (SQLException ex) {
            throw new DaoException("Error creating group member", ex);
        }

        return member;
    }



    @Override
    public GroupMember read(Integer id) throws DaoException {
        String query = """
            SELECT id, grp_id, usr_id, joined_at, left_at
            FROM grp_member
            WHERE id = ?
                """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();

            GroupMember member = null;
            if (rs.next()) {
                Timestamp leftAt = rs.getTimestamp("left_at");
                member = new GroupMember(
                    rs.getInt("id"),
                    rs.getInt("grp_id"),
                    rs.getInt("usr_id"),
                    rs.getTimestamp("joined_at").toLocalDateTime(),
                    leftAt != null ? leftAt.toLocalDateTime() : null
                );
            }

            return member;

        } catch (SQLException ex) {
            throw new DaoException("Error reading group member with ID: " + id, ex);
        }
    }


    @Override
    public List<GroupMember> readAll() throws DaoException {
        List<GroupMember> members = new ArrayList<>();
        String query = """
                SELECT *
                FROM grp_member
                """;

        try (Connection conn = datasource.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {

            while (rs.next()) {
                Timestamp leftAt = rs.getTimestamp("left_at");
                GroupMember member = new GroupMember(
                    rs.getInt("id"),
                    rs.getInt("grp_id"),
                    rs.getInt("usr_id"),
                    rs.getTimestamp("joined_at").toLocalDateTime(),
                    leftAt != null ? leftAt.toLocalDateTime() : null
                );
                members.add(member);
            }

        } catch (SQLException ex) {
            throw new DaoException("Error reading all group members", ex);
        }
        return members;
    }



    @Override
    public int update(GroupMember member) throws DaoException {
        // Update left_at to mark a member as having left the group
        String query = """
            UPDATE grp_member
            SET left_at = ?
            WHERE id = ?
            """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setTimestamp(1, member.getLeftAt() != null ? Timestamp.valueOf(member.getLeftAt()) : null);
            stat.setInt(2, member.getId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error updating group member with ID: " + member.getId(), ex);
        }
    }


    @Override
    public int delete(Integer id) throws DaoException {
        String query = "DELETE FROM grp_member WHERE id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting group member with ID: " + id, ex);
        }
    }
}
