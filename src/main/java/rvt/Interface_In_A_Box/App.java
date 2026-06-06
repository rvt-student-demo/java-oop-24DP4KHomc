package rvt.Interface_In_A_Box;

public class App {
    public static void main(String[] args) {
        // Uztaisām dažas grāmatas
        Book book1 = new Book("Fyodor Dostoevsky", "Crime and Punishment", 2.0);
        Book book2 = new Book("Robert Martin", "Clean Code", 1.0);
        Book book3 = new Book("Kent Beck", "Test Driven Development", 0.7);

        // Sagatavojam dažus CD diskus
        CD cd1 = new CD("Pink Floyd", "Dark Side of the Moon", 1973);
        CD cd2 = new CD("Wigwam", "Nuclear Nightclub", 1975);
        CD cd3 = new CD("Rendezvous Park", "Closer to Being Here", 2012);

        // Izveidojam kasti ar maksimālo ietilpību 10 kg
        Box bigBox = new Box(10.0);

        // Metam visu iekšā kastē
        bigBox.add(book1);
        bigBox.add(book2);
        bigBox.add(book3);
        bigBox.add(cd1);
        bigBox.add(cd2);
        bigBox.add(cd3);

        // Pārbaudām, kā izskatās atsevišķi elementi
        System.out.println("--- Čekojam atsevišķas mantas ---");
        System.out.println(book1);
        System.out.println(cd1);
        System.out.println();

        // Skatāmies, kas notiek mūsu kastē
        System.out.println("--- Kas notiek kastē? ---");
        System.out.println(bigBox);

        // Mēģinām iebāzt kaut ko pārāk smagu, lai redzētu, vai nostrādā svara ierobežojums
        System.out.println("\n--- Testējam svara limitu ---");
        Book heavyBook = new Book("Milzīgā Enciklopēdija", "1. Sējums", 8.0);
        System.out.println("Mēģinām iestūķēt 8kg smagu grāmatu...");
        bigBox.add(heavyBook); 
        
        // Kastes sastāvam un svaram vajadzētu palikt tādam pašam, jo 8kg vairs nelien iekšā
        System.out.println(bigBox);
        
        // Kastes kaste: Tā kā Box arī implementē Packable, varam ielikt kasti citā kastē!
        System.out.println("\n--- Testējam kasti kastē ---");
        Box shippingContainer = new Box(20.0);
        shippingContainer.add(bigBox); // Ieliekam mūsu pirmo kasti lielākā konteinerā
        System.out.println("Lielā konteinera info: " + shippingContainer);
    }
}