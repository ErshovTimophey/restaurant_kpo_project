package ru.hse.software.authentication;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrationMenu {
    private String  name = null;
    private String password = null;
    public void registrateMenu() {
        System.out.println("Добро пожаловать в Систему Ресторана,для начала, введите ваш ник:");
        Parser parser = Parser.getInstance();
        this.name = parser.parseNotEmpty();
        System.out.println("Теперь введите пароль:");
        this.password = parser.parseNotEmpty();
    }
    public static void  choiceMenu() {
        System.out.println("Введите 1, если хотите продолжить как пользователь");
        System.out.println("Введите 2, если хотите продолжить как администратор");
        System.out.println("1.Пользователь");
        System.out.println("2.Администратор");
    }
}
