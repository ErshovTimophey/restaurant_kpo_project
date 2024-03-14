package ru.hse.software.authentication;
import java.util.Scanner;

public class Parser {
    private Parser() {}
    private static Parser instance;
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }
    public int parseStroke() {
        boolean flag = false;
        int n = 0;
        while (true) {
            Scanner reader = new Scanner(System.in);
            if (reader.hasNextInt()) {
                n = reader.nextInt();
                if (!(n == 1 | n == 2)) {
                    mistakeStroke();
                } else {
                    flag = true;
                }
            } else {
                mistakeStroke();
            }
            if(flag){
                break;
            }
        }
        return n;
    }
    private void mistakeStroke() {
        System.out.println("Неверный ввод, можно ввести только цифры 1 или 2");
        System.out.println("Введите число заново");
    }
    public String parseNotEmpty() {
        while(true) {
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            if(!name.isEmpty()) {
                return name;
            }
            System.out.println("Введите непустую строку");
        }
    }
    public int parseIntStroke() {
        int n =0;
        while (true) {
            Scanner reader = new Scanner(System.in);
            if (reader.hasNextInt()) {
                n = reader.nextInt();
                if(n>=0) {
                    return n;
                }
            } else {
                System.out.println("Неверный ввод, можно ввести только неотрицательную");
                System.out.println("Повторите ввод:");
            }
        }
    }
    public Boolean parseArgs(String[] args){
        if(args.length==0){
            System.out.println("Работа программы будет завершена");
            System.out.println("Укажите через аргументы командной строки путь к файлу");
            return false;
        }
        return true;
    }

}
