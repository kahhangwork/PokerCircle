package com.pokercircle;
import javax.sql.DataSource;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//Data CRUD: create, read, update, delete
// implements

public class UserDao implements Dao<Integer, User> {
    //VARIABLES
    private DataSource datasource;

    //METHODS
    public UserDao () {
        this.datasource = DataSourceFactory.instance().getDataSource();
    }



    @Override
    public User create(User user) throws DaoException {
        // DB already handles timestamps, so we don't need to set them here
        String query = "INSERT INTO usr(email, display_name, password_hash) VALUES(?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, user.getEmail());
            stat.setString(2, user.getDisplayName());
            stat.setString(3, user.getPasswordHash());
            stat.executeUpdate();

            ResultSet generatedKeys = stat.getGeneratedKeys();
            if (generatedKeys.next())
                user.setId(generatedKeys.getInt(1));

        } catch (SQLException ex) {
            throw new DaoException("UserDao error", ex);
        }

        return user;
    }



    @Override
    public User read (Integer id) throws DaoException {
        String query = """
            SELECT usr_id, email, display_name, profile_picture, password_hash, created_at, updated_at
            FROM usr
            WHERE usr_id = ?
                """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(
                    rs.getInt("usr_id"),
                    rs.getString("email"),
                    rs.getString("display_name"),
                    rs.getString("profile_picture"),
                    rs.getString("password_hash"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }

            return user;

        } catch (SQLException ex) {
            throw new DaoException("Error reading user with ID: " + id, ex);
        }
    }


    @Override
    public List<User> readAll () throws DaoException {
        List<User> users = new ArrayList<>();
        String query = """
                SELECT *
                FROM usr
                """;

        try (Connection conn = datasource.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(query)) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("usr_id"),
                    rs.getString("email"),
                    rs.getString("display_name"),
                    rs.getString("profile_picture"),
                    rs.getString("password_hash"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
                users.add(user);
            }

        } catch (SQLException ex) {
            throw new DaoException("Error reading all users", ex);
        }
        return users;
    }




    @Override
    public int update (User user) throws DaoException {
        // DB already handles timestamps, so we don't need to set them here
        String query = """
            UPDATE usr
            SET email = ?, display_name = ?, profile_picture = ?, password_hash = ?, updated_at = CURRENT_TIMESTAMP
            WHERE usr_id = ?
            """;

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setString(1, user.getEmail());
            stat.setString(2, user.getDisplayName());
            stat.setString(3, user.getProfilePicture());
            stat.setString(4, user.getPasswordHash());
            stat.setInt(5, user.getId());

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error updating user", ex);
        }
    }


    @Override
    public int delete (Integer id) throws DaoException {
        String query = "DELETE FROM usr WHERE usr_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stat = conn.prepareStatement(query)) {
            stat.setInt(1, id);

            return stat.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Error deleting user", ex);
        }
    }
}