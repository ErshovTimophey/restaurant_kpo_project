package ru.hse.software.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.hse.software.objects.Order;
import ru.hse.software.interfaces.SystemUsers;

import java.io.Serializable;
import java.util.ArrayList;
@Getter @Setter
@Builder
public class  User implements SystemUsers, Serializable {
    @Builder.Default
     public ArrayList<Order> allTimeOrders = new ArrayList<>();
    @Builder.Default
     public ArrayList<Order> orders = new ArrayList<>();
    @Builder.Default
     private String name = null;
    @Builder.Default
     private String password = null;
    public User(ArrayList<Order> allTimeOrders,ArrayList<Order> orders, String name,String password ) {
        this.name =name;
        this.password = password;
        this.allTimeOrders = allTimeOrders;
        this.orders = orders;
    }
    public User() {}
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
