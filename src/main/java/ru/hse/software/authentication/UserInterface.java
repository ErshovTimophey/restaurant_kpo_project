package ru.hse.software.authentication;

import ru.hse.software.controllers.OrderController;
import ru.hse.software.interfaces.SystemUsers;
import ru.hse.software.objects.Dish;
import ru.hse.software.objects.Menu;
import ru.hse.software.objects.Order;
import ru.hse.software.objects.Review;
import ru.hse.software.serialization.DataSerializer;
import ru.hse.software.serialization.SerializeData;
import ru.hse.software.users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private UserInterface() {}
    private static UserInterface instance;
    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }
    public static SystemUsers user = User.builder().build();
    int roleNumber;
    public static ArrayList<Review> reviews = new ArrayList<>();
    public void runStartMenu(String path) throws IOException, ClassNotFoundException {
        DataSerializer.StartSerialization(path);
        System.out.println("Введите 1, если хотите войти и 2, если хотите зарегестрироваться в системе");
        System.out.println("1.Войти");
        System.out.println("2.Зарегестрироваться");
        Parser parser = Parser.getInstance();
        int number = parser.parseStroke();

        if(number == 1) {
            Boolean flag = true;
            while (flag){
                if(!logIn()){
                    System.out.println("Введите 1, если попробовать войти заново");
                    System.out.println("Введите 2, если хотите начать регистрацию");
                    System.out.println("1.Войти");
                    System.out.println("2.Зарегистрироваться");
                    int num = parser.parseStroke();
                    if (num==1){

                        continue;
                    }
                    else {
                        while (!registration());
                        flag=false;
                    }
                }
                else{
                    flag=false;
                }
            }
        }else {
            while(!registration());
        }
        if(roleNumber == 1){
            runUserInterface();
        }
        else {
            runAdminInterface();
        }
    }
    public Boolean logIn() {
        RegistrationMenu.choiceMenu();
        Parser parser = Parser.getInstance();
        if(parser.parseStroke()==1) {
            roleNumber = 1;
            return userAuthMenu();
        }else {
            roleNumber = 2;
            return adminAuthMenu();
        }
    }
    public Boolean registration() {
        RegistrationMenu.choiceMenu();
        Parser parser = Parser.getInstance();
        if(parser.parseStroke()==1) {
            roleNumber = 1;
            return userRegistrMenu();
        }else {
            roleNumber = 2;
            return adminRegistrMenu();
        }
    }
    public Boolean userRegistrMenu(){
        RegistrationMenu registrationMenu = new RegistrationMenu();
        registrationMenu.registrateMenu();
        AuthService authService = AuthService.getInstance();
        return authService.registerUser(registrationMenu.getName(),registrationMenu.getPassword());
    }
    public Boolean adminRegistrMenu(){
        RegistrationMenu registrationMenu = new RegistrationMenu();
        registrationMenu.registrateMenu();
        AuthService authService = AuthService.getInstance();
        return authService.registerAdministartor(registrationMenu.getName(),registrationMenu.getPassword());
    }
    public Boolean userAuthMenu(){
        RegistrationMenu registrationMenu = new RegistrationMenu();
        registrationMenu.registrateMenu();
        AuthService authService = AuthService.getInstance();
        return authService.authenticateUser(registrationMenu.getName(),registrationMenu.getPassword());
    }
    public Boolean adminAuthMenu(){
        RegistrationMenu registrationMenu = new RegistrationMenu();
        registrationMenu.registrateMenu();
        AuthService authService = AuthService.getInstance();
        return authService.authenticateAdministrator(registrationMenu.getName(),registrationMenu.getPassword());
    }

    public void runAdminInterface() throws IOException {
        boolean exit = false;
        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1. Показать служебную информацию");
            System.out.println("2. Посмотреть актуальное меню");
            System.out.println("3. Редактировать меню");
            System.out.println("4. Посмотреть список доступных блюд");
            System.out.println("5. Редактировать список блюд(убрать блюдо/добавить новое)");
            System.out.println("6. Редактировать конкретное блюдо из меню(замена блюда, исправление:количества, времени готовки, цены)");
            System.out.println("7. Выйти из системы");
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            switch (name) {
                case "1" -> {
                    System.out.println("Зарегистрированные пользователи:");
                    System.out.println(AuthService.registeredUsers);
                    System.out.println("Зарегистрированные администраторы:");
                    System.out.println(AuthService.registeredAdministrators);
                    System.out.println("Актуальное меню:");
                    System.out.println(Menu.dishes);
                    System.out.println("Отзывы:");
                    System.out.println(reviews);
                    System.out.println("Средняя оценка блюд:");
                    if(!reviews.isEmpty()){
                        int sum =0;
                        for (Review review:reviews){
                            sum+= review.mark;
                        }
                        System.out.println("Средняя оценка = " + (double)sum/reviews.size() + "\n");
                    }else{
                        System.out.println("Ни одно оценки ещё не было выставлено \n");
                    }
                    break;
                }
                case "2" -> {
                    ArrayList<Dish> dishes = Menu.dishes;
                    if(dishes.isEmpty()){
                        System.out.println("Актуальное меню пока пустое");
                    }
                    else {
                        for (Dish dish : dishes) {
                            System.out.printf("%s", dish);
                        }
                    }
                    break;
                }
                case "3"-> {
                    System.out.println("Выберите действие:");
                    System.out.println("1. Добавить блюдо в меню");
                    System.out.println("2. Убрать блюдо из меню");
                    Parser parser = Parser.getInstance();
                    int number = parser.parseStroke();
                    if(number == 1) {
                        Menu.addDish();
                    }else {
                        Menu.removeDish();
                    }
                    break;
                }
                case "4"-> {
                    if(Dish.dishes.isEmpty()){
                        System.out.println("Список достуных блюд пуст");
                    }
                    else {
                        ArrayList<Dish> dishes = Dish.dishes;
                        for (Dish dish : dishes) {
                            System.out.printf("%s", dish);
                        }
                    }
                    break;
                }
                case "5" -> {
                    System.out.println("Выберите действие:");
                    System.out.println("1. Добавить блюдо");
                    System.out.println("2. Убрать блюдо");
                    Parser parser = Parser.getInstance();
                    int number = parser.parseStroke();
                    if(number == 1) {
                        Dish.addDish();
                    }else {
                        Dish.removeDish();
                    }
                    break;
                }
                case "6" -> {
                    System.out.println("Введите название блюда, которое хотите изменить:");
                    Parser parser = Parser.getInstance();
                    String dishName = parser.parseNotEmpty();
                    for (Dish dish : Menu.dishes) {
                        if (dish.getName().equals(dishName)) {
                            System.out.println("Выберите, что хотите изменить:");
                            System.out.println("1. Количество");
                            System.out.println("2. Время приготовления");
                            System.out.println("3. Цену");
                            int option = parser.parseIntStroke();
                            switch (option) {
                                case 1 -> {
                                    System.out.println("Введите новое количество:");
                                    int quantity = parser.parseIntStroke();
                                    dish.setQuantity(quantity);
                                    System.out.println("Количество изменено успешно.");
                                }
                                case 2 -> {
                                    System.out.println("Введите новое время приготовления:");
                                    int timeOfCooking = parser.parseIntStroke();
                                    dish.setTimeOfCooking(timeOfCooking);
                                    System.out.println("Время приготовления изменено успешно.");
                                }
                                case 3 -> {
                                    System.out.println("Введите новую цену:");
                                    int price = parser.parseIntStroke();
                                    dish.setPrice(price);
                                    System.out.println("Цена изменена успешно.");
                                }
                                default -> System.out.println("Некорректный выбор.");
                            }
                        }
                    }
                    break;
                }
                case "7" -> {
                    exit = true;
                    if(DataSerializer.savedInf == null){
                        SerializeData data = new SerializeData(AuthService.registeredUsers, AuthService.registeredAdministrators, Menu.dishes, 0, null,reviews);
                        DataSerializer.saveData(data);
                        System.out.println("Данные сохранены. Программа завершена.");
                        break;
                    }
                    else {
                        SerializeData data = new SerializeData(AuthService.registeredUsers, AuthService.registeredAdministrators, Menu.dishes, 0, DataSerializer.savedInf.allTimeOrders,reviews);
                        DataSerializer.saveData(data);
                        System.out.println("Данные сохранены. Программа завершена.");
                        break;
                    }

                }
                default -> {
                    System.out.println("Неверный ввод. Пожалуйста, выберите действие от 1 до 7.");
                }
            };
        }
    }
    public void runUserInterface() throws IOException {
        boolean exit = false;

        OrderController orderController = new OrderController(user);
        User currentUser =(User) user;
        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1. Новый заказ");
            System.out.println("2. Заказы");
            System.out.println("3. Выйти из системы");
            int count = 2;
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            switch (name) {
                case "1" -> {
                    ArrayList<Review> comments = orderController.createNewOrder();
                    reviews.addAll(comments);
                    break;
                }
                case "2" -> {
                    orderController.manageOrders();
                    break;
                }
                case "3" -> {
                    exit = true;
                    int priceOfOrder = 0;
                    if(!currentUser.orders.isEmpty()) {
                        for (Order order : currentUser.orders) {
                            priceOfOrder += order.countIncome();
                            currentUser.allTimeOrders.add(order);
                        }
                    }

                    SerializeData data = new SerializeData(currentUser.allTimeOrders, AuthService.registeredUsers, AuthService.registeredAdministrators, Menu.dishes, reviews);
                    DataSerializer.saveData(data);
                    System.out.println("Данные сохранены. Программа завершена.");
                    break;

                }
                default -> {
                    System.out.println("Неверный ввод. Пожалуйста, выберите действие от 1 до 3.");
                }
            };
        }
    }
}
