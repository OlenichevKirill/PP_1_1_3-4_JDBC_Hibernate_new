package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User\n" +
                    "(\n" +
                    "    ID        BIGINT PRIMARY KEY NOT NULL auto_increment,\n" +
                    "    NAME      TEXT                 NOT NULL,\n" +
                    "    LAST_NAME TEXT                 NOT NULL,\n" +
                    "    AGE       TINYINT              NOT NULL\n" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS User;");
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO USER(NAME, LAST_NAME, AGE) values " +
                    "(?, ?, ?);")) {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                ps.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("User with the name: " + name + " added to the database");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {

            }
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {

            }
        }
    }

    public void removeUserById(long id) {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM User WHERE ID = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {

            }
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery("select * from User");
            while (rs.next()) {
                User user = new User(rs.getString("NAME"), rs.getString("LAST_NAME")
                        , rs.getByte("AGE"));
                user.setId(rs.getLong("ID"));
                listUser.add(user);
            }
        } catch (SQLException e) {

        }
        return listUser;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM User;");
        } catch (SQLException e) {

        }
    }
}
