package ru.hse.software.serialization;
import ru.hse.software.objects.Menu;
import ru.hse.software.authentication.AuthService;
import ru.hse.software.authentication.UserInterface;

import java.io.*;
import java.util.ArrayList;

public class DataSerializer {
    public static String path = null;
    public static SerializeData savedInf;
    public static void saveData(SerializeData data) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.close();
        }
        catch (Exception exception){
            System.out.println("Неудалось сохранить данные");
        }
    }
    public static void StartSerialization(String rootPath) throws IOException, ClassNotFoundException {
        try {
            path = rootPath;
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            SerializeData savedApp = (SerializeData) objectInputStream.readObject();
            savedInf = savedApp;
            AuthService.registeredUsers=savedApp.registeredUsers;
            AuthService.registeredAdministrators=savedApp.registeredAdministrators;
            Menu.dishes = savedApp.menuDishes;
            if(!savedApp.reviews.isEmpty()) {
                UserInterface.reviews = savedApp.reviews;
            }
            else{
                UserInterface.reviews =new ArrayList<>();
            }

        }
        catch (FileNotFoundException ignored){}//Просто первый запуск файла
        catch (Exception exception){
            System.out.println("Ошибка чтения из файла");
        }
    }

}
