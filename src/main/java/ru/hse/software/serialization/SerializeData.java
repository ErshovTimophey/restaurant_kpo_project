package ru.hse.software.serialization;

import lombok.ToString;
import ru.hse.software.objects.Dish;
import ru.hse.software.objects.Order;
import ru.hse.software.objects.Review;
import ru.hse.software.users.Administrator;
import ru.hse.software.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
@ToString(includeFieldNames=true)
public class SerializeData implements Serializable {
    public  HashMap<String, User> registeredUsers = new HashMap<>();
    public HashMap<String, Administrator> registeredAdministrators = new HashMap<>();
    public  ArrayList<Dish> menuDishes = new ArrayList<>();
    public ArrayList<Order> allTimeOrders = new ArrayList<>();
    public int priceOfOrders = 0;
    public ArrayList<Review> reviews = new ArrayList<>();
    public SerializeData(HashMap<String, User> registeredUsers, HashMap<String, Administrator> registeredAdministrators
                    ,ArrayList<Dish> menuDishes, int priceOfOrders,ArrayList<Order> allTimeOrders,ArrayList<Review> reviews){
        this.registeredAdministrators = registeredAdministrators;
        this.registeredUsers = registeredUsers;
        this.menuDishes = menuDishes;
        this.priceOfOrders=priceOfOrders;
        this.allTimeOrders = allTimeOrders;
        this.reviews = reviews;
    }
    public SerializeData(ArrayList<Order> allTimeOrders,HashMap<String, User> registeredUsers, HashMap<String, Administrator> registeredAdministrators
                ,ArrayList<Dish> menuDishes,ArrayList<Review> reviews ){
        this.allTimeOrders = allTimeOrders;
        this.registeredUsers=registeredUsers;
        this.registeredAdministrators=registeredAdministrators;
        this.menuDishes = menuDishes;
        this.reviews = reviews;
    }

}
