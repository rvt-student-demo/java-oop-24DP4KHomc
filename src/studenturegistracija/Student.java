package studenturegistracija;

public class Student {

    // mainīgie
    String vards;
    String uzvards;
    String epasts;
    String personasKods;
    String registracijasDatums;

    publi Students(String vards, String uzvards, String epasts,
                   String personasKods, String registracijasDatums) {

    // Šis saglabā ievaditos string 
    this.vards = vards;
    this.uzvards = uzvards;
    this.epasts = epasts;
    this.personasKods = personasKods;
    this.registracijasDatums = registracijasDatums;
    }

    // pārveido šo visu uz CSV formātu, lai to varētu saglabāt failā
    public String toCSV() {
        return vards + "," + uzvards + "," + epasts + "," + personasKods + "," + registracijasDatums;
        }
    }
    

