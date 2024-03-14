package ru.hse.software;

import ru.hse.software.authentication.Parser;
import ru.hse.software.authentication.UserInterface;

import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        UserInterface userInterface = UserInterface.getInstance();
        Boolean flag = Parser.getInstance().parseArgs(args);
        if(!flag) return;
        userInterface.runStartMenu(args[0]);
    }
}