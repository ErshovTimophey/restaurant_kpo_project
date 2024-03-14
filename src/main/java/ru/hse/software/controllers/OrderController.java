package ru.hse.software.controllers;

import ru.hse.software.authentication.Parser;
import ru.hse.software.interfaces.SystemUsers;
import ru.hse.software.objects.Dish;
import ru.hse.software.objects.Menu;
import ru.hse.software.objects.Order;
import ru.hse.software.objects.Review;
import ru.hse.software.users.User;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.PriorityQueue;

public class OrderController {
    User user;
    private PriorityQueue<Order> orderQueue;
    ArrayList<Review> reviews = new ArrayList<>();
    public OrderController(SystemUsers user) {
        this.orderQueue = new PriorityQueue<>((o1, o2) -> o1.getCreationTime().compareTo(o2.getCreationTime()));
        this.user = (User) user;
    }
    public void addOrder(Order order) {
        orderQueue.offer(order);
    }
    public ArrayList<Review> processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(5); // Пул потоков для параллельной обработки заказов
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            //for (Order order : user.orders) {
            executor.execute(() -> {
                // Проверяем, что заказ не отменён
                if (Objects.equals(order.getStatus(), "PAID")) {
                    order.setStatus("IN_PROCESS");
                    System.out.println("Заказ в процессе готовки.");
                    // Симуляция времени приготовления
                    try {
                        Thread.sleep(10000); // 5 секунд
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Objects.equals(order.getStatus(), "CANCELED")){
                        return;
                    }
                    order.setStatus("READY");
                    System.out.println("Заказ готов.");
                }
            });
        }
        executor.shutdown(); // Остановка пула потоков
        return reviews;
    }


    public ArrayList<Review> createNewOrder() {
        Order newOrder = new Order(user);
        Scanner scanner = new Scanner(System.in);

        if (!Menu.dishes.isEmpty()) {
            while (true) {
                System.out.println("Доступные блюда:");
                int counter = 0;
                for (Dish dish : Menu.dishes) {
                    System.out.printf("%d. ", ++counter);
                    System.out.println(dish.toString());
                }
                System.out.println("Выберите блюдо для заказа (номер) или введите '0' для завершения заказа:");
                int choice = Parser.getInstance().parseIntStroke();
                if (choice == 0) {
                    break;
                } else if (choice > 0 && choice <= Menu.dishes.size()) {
                    // Добавляем выбранное блюдо в заказ
                    Dish selectedDish = Menu.dishes.get(choice - 1);
                    newOrder.addDish(selectedDish);
                    System.out.println(selectedDish.getName() + " добавлен в заказ.");
                } else {
                    System.out.println("Некорректный выбор.");
                }
            }

            newOrder.payOrder();
            addOrder(newOrder);
            System.out.println("Введите 1, если хотите оставить отзыв и 2, если не хотите:");
            if(Parser.getInstance().parseStroke() == 1) {
                leaveReview();
            }
            if (Objects.equals(newOrder.getStatus(), "PAID")) {
                return processOrders();
            } else {
                System.out.println("Ошибка оплаты заказа. Заказ не будет обработан.");
            }
        } else {
            System.out.println("Меню пустое, вы не можете создать новый заказ, пока администратор не добавит блюдо в меню");
        }
        return new ArrayList<>();
    }


    public void manageOrders() {
        System.out.println("Введите соответствующую цифру");
        System.out.println("1. Смотреть информацию о заказах");
        System.out.println("2. Отменить заказ");
        int choice = Parser.getInstance().parseStroke();
        if(choice == 1) {
            System.out.println("Предыдущие заказы:");
            for(Order order:user.allTimeOrders) {
                System.out.println(order);
            }
            System.out.println("Текущие заказы:");
            for(Order order:user.orders) {
                System.out.println(order);
            }
        }
        else {
            Scanner scanner = new Scanner(System.in);
            if(!user.orders.isEmpty()) {
                System.out.println("Введите номер заказа, который вы хотите изменить:");
                int counter = 0;
                for (Order order : user.orders) {
                    System.out.printf("Заказ номер:%d ", ++counter);
                    System.out.println(order);
                }
                int orderNumber = scanner.nextInt();
                if (orderNumber > 0 && orderNumber <= user.orders.size()) {
                    Order orderToManage = user.orders.get(orderNumber - 1);
                    orderToManage.cancelOrder();
                } else {
                    System.out.println("Неверный номер заказа.");
                }
            }
            else{
                System.out.println("Список текущих заказов пустой");
            }
        }
    }
    public void leaveReview(){
        Scanner scanner = new Scanner(System.in);
        Boolean flag = false;
        while (!flag) {
            System.out.println("Введите оценку от 1 до 5:");
            int orderNumber = Parser.getInstance().parseIntStroke();
            if (orderNumber > 0 && orderNumber <= 5) {
                System.out.println("Введите текстовый комментарий:");
                reviews.add(Review.builder().review(Parser.getInstance().parseNotEmpty()).mark(orderNumber).build());
                flag = true;
                System.out.println("Ваш отзыв успешно оставлен");
            } else {
                System.out.println("Неверно введённая цифраы.");
            }
        }
    }
}
