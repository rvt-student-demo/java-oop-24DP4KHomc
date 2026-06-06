package rvt.TodoDB;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializējam mūsu datubāzes klasi
        TodoDB db = new TodoDB();

        System.out.println("=== 1. TESTĒJAM PIEVIENOŠANU ===");
        db.add("Iemācīties SQL pamatus");
        db.add("Nodot programmēšanas uzdevumu");
        db.add("Atpūsties un uzkapāt kādu spēli");

        System.out.println("\n=== 2. SKATĀMIES VISUS UZDEVUMUS ===");
        List<String> saraksts = db.findAll();
        for (String uzdevums : saraksts) {
            System.out.println(uzdevums);
        }

        System.out.println("\n=== 3. TESTĒJAM DZĒŠANU pēc ID ===");
        // Izdzēšam, piemēram, pirmo uzdevumu
        db.removeById(1);

        System.out.println("\n=== 4. SARAKSTS PĒC DZĒŠANAS ===");
        saraksts = db.findAll();
        for (String uzdevums : saraksts) {
            System.out.println(uzdevums);
        }
    }
}