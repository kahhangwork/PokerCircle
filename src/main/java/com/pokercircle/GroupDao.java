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

public class GroupDao implements Dao<Integer, Group> {

    private DataSource datasource;

    public GroupDao() {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }
    
    @Override
    public Group create(Group group) throws DaoException {
        String query = "INSERT INTO grp(name, created_by, description) VALUES(?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, group.getName());
            stat.setInt(2, group.getCreatedBy());
            stat.setString(3, group.getDescription());
            stat.executeUpdate();
            ResultSet generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next())
                group.setId(generatedKeys.getInt(1));
        } catch (SQLException ex) {
            throw new DaoException("Error creating group", ex);
        }
        return group;
    }

    @Override
    public Group read(Integer id) throws DaoException {
        String query = """
            SELECT id, name, description, created_by, created_at, updated_at
            FROM grp
            WHERE id = ?  
                """;
        
        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();

            Group group = null;
            if (rs.next()) {
                return new Group(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("created_by"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
            } return group;
        } catch (SQLException ex) {
            throw new DaoException("Error reading group with id " + id, ex);
        }
    }


    @Override
    public List<Group> readAll() throws DaoException {
        String query = """
            SELECT id, name, description, created_by, created_at, updated_at
            FROM grp
                """;
        List<Group> groups = new ArrayList<>();

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                groups.add(new Group(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("created_by"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException ex) {
            throw new DaoException("Error reading all groups", ex);
        }
        return groups;
    }

    @Override
    public int update(Group group) throws DaoException {
        String query = "UPDATE grp SET name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setString(1, group.getName());
            stat.setString(2, group.getDescription());
            stat.setInt(3, group.getId());

            return stat.executeUpdate();

        } catch (SQLException ex) {
            throw new DaoException("Error updating group with id " + group.getId(), ex);
        }
        
    }

    @Override
    public int delete(Integer id) throws DaoException {
        String query = "DELETE FROM grp WHERE id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);
            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting group with id " + id, ex);
        }
    }
}
