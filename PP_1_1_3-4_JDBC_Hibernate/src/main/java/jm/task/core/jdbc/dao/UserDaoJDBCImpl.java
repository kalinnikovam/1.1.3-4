package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    // получение соединения
    private Connection connection;


    public UserDaoJDBCImpl() {
    }


    @Override
    public void createUsersTable() {
        try {
            try {
                connection = Util.getConnection();
            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Ошибка при соединении");
            }
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45) NOT NULL, " +
                    "lastName VARCHAR(45) NOT NULL, " +
                    "age INTEGER NOT NULL)");
            statement.executeUpdate("ALTER TABLE users " +
                    "CHANGE id " +
                    "id INT(1) NOT NULL AUTO_INCREMENT;");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
                throw new RuntimeException(e);
            }
        }

    }

    public void dropUsersTable() {
        try {
            try {
                connection = Util.getConnection();
            } catch (SQLException e) {
                System.out.println("Ошибка при соединении");
            }
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "users", new String[] {"TABLE"});
            if (resultSet.next()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE users");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            try {
                connection = Util.getConnection();
            } catch (SQLException e) {
                System.out.println("Ошибка при соединении");
            }

            PreparedStatement ps = connection.prepareStatement("INSERT INTO users(name, lastname, age) " +
                            "VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();

            System.out.printf("User с именем - %s добавлен в базу данных", name);
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя");
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
                throw new RuntimeException(e);
            }
        }
    }

    public void removeUserById(long id) {
        try {
            try {
                connection = Util.getConnection();
            } catch (SQLException e) {
                System.out.println("Ошибка при соединении");
            }
            PreparedStatement ps = connection.prepareStatement("DELETE FROM users " +
                    "WHERE id = ?");
            ps.setInt(1, (int) id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя");
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
                throw new RuntimeException(e);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            try {
                connection = Util.getConnection();
            } catch (SQLException e) {
                System.out.println("Ошибка при соединении");
            }
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                User user = new User(name, lastName, (byte) age);
                user.setId(id);
                userList.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении пользователей");
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
                throw new RuntimeException(e);
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            try {
                connection = Util.getConnection();
            } catch (SQLException e) {
                System.out.println("Ошибка при соединении");
            }
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "users", new String[] {"TABLE"});
            if (resultSet.next()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("TRUNCATE TABLE users");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при полном удалении таблицы");
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения");
                throw new RuntimeException(e);
            }
        }
    }


}
