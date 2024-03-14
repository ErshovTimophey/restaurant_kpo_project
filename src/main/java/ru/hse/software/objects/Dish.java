package ru.hse.software.objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.hse.software.authentication.Parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


@Getter @Setter
@Builder
public class Dish implements Serializable {
    public static ArrayList<Dish> dishes = new ArrayList<>(); // сюда десериализуем блюда
    @Builder.Default
    private int price = 0; // in rubles
    @Builder.Default
    private int timeOfCooking = 0;// in minutes
    @Builder.Default
    private int quantity = 0;// in gramms
    @Builder.Default
    private String name = null;
    public Dish(int price, int timeOfCooking, int quantity, String name){
        this.price = price;
        this.quantity= quantity;
        this.timeOfCooking = timeOfCooking;
        this.name = name;
    }
    public Dish() {}
    public static void addDish() {
        //Создаём Dish
        Parser parser = Parser.getInstance();
        System.out.println("Введите название блюда:");
        String dishName = parser.parseNotEmpty();
        System.out.println("Введите время приготовления блюда в минутах:");
        int timeOfCooking  = parser.parseIntStroke();
        System.out.println("Введите цену блюда в рублях:");
        int price  = parser.parseIntStroke();
        System.out.println("Введите количество грамм в блюде:");
        int quantity  = parser.parseIntStroke();
        dishes.add(new Dish(price,timeOfCooking,quantity,dishName));
    }
    public static void removeDish() {
        if(!dishes.isEmpty()) {
            Parser parser = Parser.getInstance();
            System.out.printf("Список доступных блюд: %s", dishes);
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
            System.out.println("Список достуных блюд пуст");
        }
    }
    @Override
    public String toString() {
        return "Блюдо{" +
                "название='" + name + '\'' +
                ", цена=" + price +
                ", время приготовления=" + timeOfCooking +
                ", количество=" + quantity +
                '}';
    }

}
