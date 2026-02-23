package rvt.studentreg;

// Datu modelis vienam studentam.
// Šī klase tikai glabā datus un pārvērš tos CSV rindā.
public class Students {

    private String vards;
    private String uzvards;
    private String epasts;
    private String personasKods;
    private String registresanasDatumsLaiks;

    public Students(String vards, String uzvards, String epasts,
                    String personasKods, String registresanasDatumsLaiks) {
        this.vards = vards;
        this.uzvards = uzvards;
        this.epasts = epasts; 
        this.personasKods = personasKods;
        this.registresanasDatumsLaiks = registresanasDatumsLaiks;
    }

    // ===== Getteri =====
    public String getVards() { return vards; }
    public String getUzvards() { return uzvards; }
    public String getEpasts() { return epasts; }
    public String getPersonasKods() { return personasKods; }
    public String getRegistresanasDatumsLaiks() { return registresanasDatumsLaiks; }

    // ===== Setteri (izmanto redigesana) =====
    public void setVards(String vards) { this.vards = vards; }
    public void setUzvards(String uzvards) { this.uzvards = uzvards; }
    public void setEpasts(String epasts) { this.epasts = epasts; }

    // Pārvērš objektu par vienu CSV rindu
    public String uzCsvRindu() {
        return String.join(",", vards, uzvards, epasts, personasKods, registresanasDatumsLaiks);
    }

    // Izveido Students objektu no CSV rindas
    public static Students noCsvRindas(String rinda) {
        String[] dalas = rinda.split(",", -1);
        if (dalas.length != 5) return null;
        return new Students(dalas[0], dalas[1], dalas[2], dalas[3], dalas[4]);
    }
}
