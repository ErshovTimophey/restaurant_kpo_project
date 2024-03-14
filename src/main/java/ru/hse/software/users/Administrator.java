package ru.hse.software.users;

import lombok.Getter;
import lombok.Setter;
import ru.hse.software.objects.Menu;
import ru.hse.software.interfaces.SystemUsers;

import java.io.Serializable;

@Getter @Setter
public class Administrator implements SystemUsers, Serializable {
    public Administrator(String username,String password){
        name = username;
        this.password= password;
        this.menu = new Menu();
    }
    private Menu menu;
    private String name = null;
    private String password = null;
    @Override
    public String toString() {
        return "Administrator{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
