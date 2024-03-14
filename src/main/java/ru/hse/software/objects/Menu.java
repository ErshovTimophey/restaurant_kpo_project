package ru.hse.software.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.hse.software.authentication.Parser;
import ru.hse.software.objects.Dish;

@ToString(includeFieldNames=true)
@Getter @Setter
public class Menu implements Serializable {
    public static ArrayList<Dish> dishes = new ArrayList<>();
    public static void addDish() {
        Parser parser = Parser.getInstance();
        System.out.println("Введите нужную цифру:");
        System.out.println("1. Хочу добавить новое блюдо в меню");
        System.out.println("2. Хочу выбрать блюдо из списка доступных");
        if(parser.parseStroke() == 1) {
            System.out.println("Введите название блюда:");
            String dishName = parser.parseNotEmpty();
            System.out.println("Введите время приготовления блюда в минутах:");
            int timeOfCooking = parser.parseIntStroke();
            System.out.println("Введите цену блюда в рублях:");
            int price = parser.parseIntStroke();
            System.out.println("Введите количество грамм в блюде:");
            int quantity = parser.parseIntStroke();
            dishes.add(Dish.builder().price(price).timeOfCooking(timeOfCooking).quantity(quantity).name(dishName).build());
            Dish.dishes.add(Dish.builder().price(price).timeOfCooking(timeOfCooking).quantity(quantity).name(dishName).build());
            System.out.println("Блюдо успешно добавлено в меню");
        }
        else {
            if(!Dish.dishes.isEmpty()) {
                boolean flag = true;
                while (flag) {
                    System.out.printf("Список блюд из меню: %s", Dish.dishes);
                    System.out.print("\n");
                    System.out.println("Введите название блюда, которое хотите добавить:");
                    String dishName = parser.parseNotEmpty();
                    for (Dish meal : Dish.dishes) {
                        if (Objects.equals(meal.getName(), dishName)) {
                            dishes.add(meal);
                            System.out.println("Блюдо было успешно добавлено");
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        System.out.println("Блюда с таким назанием не было найдено");
                    }
                }
            }
            else {
                System.out.println("Список достуных блюд пуст");
            }
        }
    }
    public static void removeDish() {
        if(!dishes.isEmpty()) {
            Parser parser = Parser.getInstance();
            System.out.printf("Список блюд из меню: %s", dishes);
            System.out.println("Введите название блюда, которое хотите удалить:");
            String dishName = parser.parseNotEmpty();
            //Запрашиваем название dish у пользователя
            for (Dish meal : dishes) {
                if (Objects.equals(meal.getName(), dishName)) {
                    dishes.remove(meal);
                    System.out.println("Блюдо было успешно удалено");
                    return;
                }
            }
            System.out.println("Блюда с таким назанием не было найдено");
        }
        else {
            System.out.println("Меню пустое");
        }
    }

}
