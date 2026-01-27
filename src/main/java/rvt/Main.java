package rvt;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        TodoList list = new TodoList();
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(list, scanner);
        ui.start();
    }
}


class TodoList {
    private ArrayList<String> tasks = new ArrayList<>();
    private final String filePath = "todo.csv";

    public TodoList() {
        loadFromFile();
    }

    public void add(String task) {
        tasks.add(task);
        saveToFile();
    }

    public void print() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i));
        }
    }

    public void remove(int number) {
        tasks.remove(number - 1);
        saveToFile();
    }


    private void loadFromFile() {
        try (Scanner fileReader = new Scanner(new File(filePath))) {
            if (fileReader.hasNextLine()) {
                fileReader.nextLine(); // skip header
            }
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    tasks.add(parts[1]);
                }
            }
        } catch (Exception e) {
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("id,task");
            for (int i = 0; i < tasks.size(); i++) {
                writer.println((i + 1) + "," + tasks.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error writing file.");
        }
    }
}

class UserInterface {
    private TodoList todoList;
    private Scanner scanner;

    public UserInterface(TodoList todoList, Scanner scanner) {
        this.todoList = todoList;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.print("Command: ");
            String command = scanner.nextLine();

            if (command.equals("stop")) {
                break;
            }

            if (command.equals("add")) {
                System.out.print("To add: ");
                String task = scanner.nextLine();
                todoList.add(task);
            }

            if (command.equals("list")) {
                todoList.print();
            }

            if (command.equals("remove")) {
                System.out.print("Which one is removed? ");
                int id = Integer.valueOf(scanner.nextLine());
                todoList.remove(id);
            }
        }
    }

}
