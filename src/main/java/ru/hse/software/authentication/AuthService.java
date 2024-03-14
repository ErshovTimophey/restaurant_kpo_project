package ru.hse.software.authentication;

import ru.hse.software.users.Administrator;
import ru.hse.software.users.User;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.Getter;
import lombok.Setter;
import ru.hse.software.serialization.DataSerializer;

@Getter @Setter
public class AuthService {
    private AuthService() {}
    private static AuthService instance;
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }
    public static  HashMap<String, User> registeredUsers = new HashMap<>();
    public static  HashMap<String, Administrator> registeredAdministrators = new HashMap<>();
    public Boolean registerUser(String username,String password) {
        if (registeredUsers.containsKey(username)) {
            System.out.printf("Пользователь с именем %s уже зарегистрирован.", username);
            System.out.print("\n");
            return false;
        }

        String hashedPassword = hashPassword(password);
        User newUser = User.builder()
                .orders(new ArrayList<>())
                .allTimeOrders(new ArrayList<>())
                .name(username)
                .password(hashedPassword)
                .build();
        registeredUsers.put(username,newUser);

        System.out.printf("Пользователь %s успешно аутентифицирован.", username);
        System.out.print("\n");
        UserInterface.user= User.builder()
                .orders(new ArrayList<>())
                .allTimeOrders(new ArrayList<>())
                .name(username)
                .password(hashedPassword)
                .build();
        return true;
    }
    public Boolean registerAdministartor(String username,String password) {
        if (registeredAdministrators.containsKey(username)) {
            System.out.printf("Пользователь с именем %s уже зарегистрирован.", username);
            System.out.print("\n");
            return false;
        }

        String hashedPassword = hashPassword(password);
        Administrator newUser = new Administrator(username, hashedPassword);
        registeredAdministrators.put(username,newUser);

        System.out.printf("Пользователь %s успешно аутентифицирован.", username);
        System.out.print("\n");
        UserInterface.user= new Administrator(username, password);
        return true;
    }

    public Boolean authenticateUser(String username,  String password){
        User user = registeredUsers.get(username);

        if (user != null && verifyPassword(password, user.getPassword())) {
            System.out.printf("Пользователь %s успешно аутентифицирован.", username);
            System.out.print("\n");
            user.allTimeOrders=DataSerializer.savedInf.allTimeOrders;
            UserInterface.user= user;
            return true;
        } else {
            System.out.println("Ошибка аутентификации. Проверьте имя пользователя и пароль.");
            System.out.print("\n");
            return false;
        }
    }
    public Boolean authenticateAdministrator(String username,  String password){
        Administrator admin = registeredAdministrators.get(username);

        if (admin != null && verifyPassword(password, admin.getPassword())) {
            System.out.printf("Пользователь %s успешно аутентифицирован.", username);
            System.out.print("\n");
            UserInterface.user= admin;
            return true;
        } else {
            System.out.println("Ошибка аутентификации. Проверьте имя пользователя и пароль.");
            System.out.print("\n");
            return false;
        }
    }

    private String hashPassword( String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private Boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
