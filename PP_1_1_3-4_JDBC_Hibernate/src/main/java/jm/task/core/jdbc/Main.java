package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        User user1 = new User("Vladimir", "Putin", (byte) 64);
        userList.add(user1);
        User user2 = new User("German", "Sevostyanov", (byte) 30);
        userList.add(user2);
        User user3 = new User("Marina", "Kalinnikova", (byte) 18);
        userList.add(user3);
        User user4 = new User("Merlin", "Monro", (byte) 45);
        userList.add(user4);

        UserService userService = new UserServiceImpl();

        //Создание таблицы

        userService.createUsersTable();

        //Добавляем пользователей

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        //Получаем всех пользователей

        userService.getAllUsers();

        //Очичаем таблицу

        userService.removeUserById(1);
        userService.removeUserById(2);
        userService.removeUserById(3);
        userService.removeUserById(4);

        // Удаляем таблицу

        userService.cleanUsersTable();

        userService.getAllUsers();

    }
}
