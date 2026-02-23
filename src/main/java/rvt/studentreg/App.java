package rvt.studentreg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Galvena CLI klase. Apstrada lietotaja komandas un izsauc atbilstoso logiku.
public class App {

    // Datuma/laika formats, kads tiks saglabats CSV faila
    private static final DateTimeFormatter DT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        // Scanner lasa lietotaja ievadi no konsoles
        Scanner skeneris = new Scanner(System.in);

        // Klase, kas stradaa ar CSV failu
        FailuApstrade faili = new FailuApstrade("data/studenti.csv");

        // Galvena biznesa logika (registre, dzes, redige)
        Registrs registrs = new Registrs(faili);

        // Bezgaligs cikls, lai programma stradatu lidz lietotajs izvelas exit
        while (true) {
            System.out.print("\nIevadi komandu (register/show/remove/edit/exit): ");
            String komanda = skeneris.nextLine().trim().toLowerCase(); 

            try {
                // Izvelas darbibu pec lietotaja komandas
                switch (komanda) {
                    case "register" -> register(skeneris, registrs);
                    case "show" -> show(registrs);
                    case "remove" -> remove(skeneris, registrs);
                    case "edit" -> edit(skeneris, registrs);
                    case "exit" -> {
                        System.out.println("Programma aptureta.");
                        return; // iziet no programmas
                    }
                    default -> System.out.println("Nezinama komanda.");
                }

            // Lietotaja ievades kludas (piem., slikts epasts)
            } catch (IevadesKluda e) {
                System.out.println("Klauda: " + e.getMessage());

            // Citas negaiditas sistemas kludas
            } catch (RuntimeException e) {
                System.out.println("Sistemas klauda: " + e.getMessage());
            }
        }
    }

    // ===== REGISTER =====
    // Nolasa datus, parbauda ar Validatori un pievieno jaunu studentu CSV
    private static void register(Scanner skeneris, Registrs registrs) {

        // Prasam un validējam katru lauku
        String vards = prasitUnParbaudit(skeneris, "Vards", Validatori::parbaudiVardu);
        String uzvards = prasitUnParbaudit(skeneris, "Uzvards", Validatori::parbaudiUzvardu);
        String epasts = prasitUnParbaudit(skeneris, "Epasts", Validatori::parbaudiEpastu);
        String personasKods =
                prasitUnParbaudit(skeneris, "Personas kods (DDMMYY-XXXXX)",
                        Validatori::parbaudiPersonasKodu);

        // Automatiski pievieno registresanas laiku
        String regDatLaiks = LocalDateTime.now().format(DT);

        // Izveido jaunu Students objektu
        Students students = new Students(
                vards.trim(),
                uzvards.trim(),
                epasts.trim(),
                personasKods.trim(),
                regDatLaiks
        );

        // Saglaba studentu caur registru
        registrs.registreStudentu(students);

        System.out.println("Students registrets.");
    }

    // ===== SHOW =====
    // Parada visus studentus tabulas veida
    private static void show(Registrs registrs) {
        TabulasIzvade.paraditStudentus(registrs.dabutVisus());
    }

    // ===== REMOVE =====
    // Dzēš studentu pēc personas koda
    private static void remove(Scanner skeneris, Registrs registrs) {
        String personasKods =
                prasitUnParbaudit(skeneris,
                        "Ievadi personas kodu dzesanai (DDMMYY-XXXXX)",
                        Validatori::parbaudiPersonasKodu);

        boolean izdzests = registrs.dzestPecPersonasKoda(personasKods.trim());

        if (izdzests) System.out.println("Students izdzests.");
        else System.out.println("Students ar so personas kodu nav atrasts.");
    }

    // ===== EDIT =====
    // Redige studenta datus pec personas koda
    private static void edit(Scanner skeneris, Registrs registrs) {
        String personasKods =
                prasitUnParbaudit(skeneris,
                        "Ievadi personas kodu redigesanai (DDMMYY-XXXXX)",
                        Validatori::parbaudiPersonasKodu);

        // Atrod esošo studentu
        Students esosais = registrs.atrastPecPersonasKoda(personasKods.trim());
        if (esosais == null)
            throw new IevadesKluda("Students ar so personas kodu nav atrasts.");

        System.out.println("Atstaj tuksu, lai nemainitu lauku.");

        // Lietotajs var ievadit jaunas vertibas
        String jaunaisVards =
                prasit(skeneris, "Jauns vards (" + esosais.getVards() + "): ");
        String jaunaisUzvards =
                prasit(skeneris, "Jauns uzvards (" + esosais.getUzvards() + "): ");
        String jaunaisEpasts =
                prasit(skeneris, "Jauns epasts (" + esosais.getEpasts() + "): ");

        // Sākotnējās vērtības
        String vards = esosais.getVards();
        String uzvards = esosais.getUzvards();
        String epasts = esosais.getEpasts();

        // Ja lietotajs kaut ko ievadija — parbaudam un atjaunojam
        if (!jaunaisVards.trim().isEmpty()) {
            Validatori.parbaudiVardu(jaunaisVards);
            vards = jaunaisVards.trim();
        }
        if (!jaunaisUzvards.trim().isEmpty()) {
            Validatori.parbaudiUzvardu(jaunaisUzvards);
            uzvards = jaunaisUzvards.trim();
        }
        if (!jaunaisEpasts.trim().isEmpty()) {
            Validatori.parbaudiEpastu(jaunaisEpasts);
            epasts = jaunaisEpasts.trim();
        }

        // Personas kods un registresanas laiks nemainas (tas ir ID)
        Students atjauninats = new Students(
                vards,
                uzvards,
                epasts,
                esosais.getPersonasKods(),
                esosais.getRegistresanasDatumsLaiks()
        );

        registrs.rediget(atjauninats);

        System.out.println("Dati atjauninati.");
    }

    // ===== Paligfunkcijas ievadei =====

    // Funkcionalais interfeiss validacijas funkcijai
    private interface Parbaude {
        void izpildit(String v);
    }

    
    // Prasa ievadi un atkarto, lidz validacija iziet veiksmigi
    private static String prasitUnParbaudit(
            Scanner skeneris,
            String nosaukums,
            Parbaude parbaude
    ) {
        while (true) {
            String ievade = prasit(skeneris, nosaukums + ": ");
            try {
                parbaude.izpildit(ievade);
                return ievade;
            } catch (IevadesKluda e) {
                System.out.println("Klauda: " + e.getMessage());
            }
        }
    }

    // Vienkarsi izvada tekstu un nolasa lietotaja ievadi
    private static String prasit(Scanner skeneris, String teksts) {
        System.out.print(teksts);
        return skeneris.nextLine();
    }
}