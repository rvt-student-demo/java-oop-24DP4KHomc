package rvt;
import java.util.Scanner;

public class Main {
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TodoApp.TodoList todoList = new TodoApp.TodoList();
        TodoApp.UserInterface ui =
                new TodoApp.UserInterface(todoList, scanner);

        ui.start();
    }
}
