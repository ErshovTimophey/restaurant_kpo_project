package ru.hse.software.objects;
import lombok.Getter;
import lombok.Setter;
import ru.hse.software.authentication.Parser;
import ru.hse.software.interfaces.SystemUsers;
import ru.hse.software.objects.Dish;
import ru.hse.software.users.User;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
public class Order implements Serializable {
    @Getter
    private LocalDateTime creationTime;

    public ArrayList<Dish> dishes = new ArrayList<>();
    private int priceOfOrder = 0;
    private  String status = "NEW";
    private transient User user;

    public Order(SystemUsers user) {
        this.user = (User) user;
        ((User) user).orders.add(this);
        this.creationTime = LocalDateTime.now();
    }

    public synchronized void addDish(Dish dish) {
        dishes.add(dish);
        priceOfOrder += dish.getPrice();
    }

    public void payOrder() {
        if (Objects.equals(status, "NEW")) {
            System.out.printf("Для подтверждения оплаты введите сумму больше или равную %d: ",priceOfOrder);
            int sum = Parser.getInstance().parseIntStroke();
            if(sum>= priceOfOrder) {
                status = "PAID";
                System.out.println("Заказ оплачен.");
            }
            else {
                status = "CANCELED";
                System.out.println("Оплата не прошла, заказ был отменён.");
            }
        } else {
            System.out.println("Невозможно оплатить заказ.");
        }
    }

    public void cancelOrder() {
        if (Objects.equals(status, "IN_PROCESS")) {
            status = "CANCELED";
            System.out.println("Заказ отменен.");
        } else {
            System.out.println("Невозможно отменить заказ.");
        }
    }

    public  int countIncome() {
        if (dishes.isEmpty()) {
            System.out.println("Пока не было заказов");
            return priceOfOrder;
        }
        return priceOfOrder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Заказ {");
        sb.append("Блюда: ").append(dishes);
        sb.append(", Общая стоимость: ").append(priceOfOrder);
        sb.append(", Статус: ").append(status);
        sb.append("}");
        return sb.toString();
    }


}
