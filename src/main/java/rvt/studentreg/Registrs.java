package rvt.studentreg;

import java.util.List;

// Galvenā biznesa loģika:
// - reģistrē
// - dzēš
// - rediģē
// - pārbauda unikālos laukus
public class Registrs {

    private final FailuApstrade faili;

    public Registrs(FailuApstrade faili) {
        this.faili = faili;
    } 

    // Pievieno jaunu studentu (ar unikālo lauku pārbaudi)
    public void registreStudentu(Students students) {
        List<Students> visi = faili.nolasitVisus();

        boolean epastsAiznemts = visi.stream()
                .anyMatch(s -> s.getEpasts().equalsIgnoreCase(students.getEpasts()));
        if (epastsAiznemts) throw new IevadesKluda("Epasts aiznemts.");

        boolean pkEksiste = visi.stream()
                .anyMatch(s -> s.getPersonasKods().equals(students.getPersonasKods()));
        if (pkEksiste) throw new IevadesKluda("Personas kods eksiste.");

        visi.add(students);
        faili.saglabatVisus(visi);
    }

    // Atgriež visus studentus
    public List<Students> dabutVisus() {
        return faili.nolasitVisus();
    }

    // Dzēš studentu pēc personas koda
    public boolean dzestPecPersonasKoda(String personasKods) {
        List<Students> visi = faili.nolasitVisus();
        int pirms = visi.size();

        visi.removeIf(s -> s.getPersonasKods().equals(personasKods));

        if (visi.size() != pirms) {
            faili.saglabatVisus(visi);
            return true;
        }
        return false;
    }

    // Atrod studentu pēc personas koda
    public Students atrastPecPersonasKoda(String personasKods) {
        return faili.nolasitVisus().stream()
                .filter(s -> s.getPersonasKods().equals(personasKods))
                .findFirst()
                .orElse(null);
    }

    // Rediģē studenta datus
    public void rediget(Students atjauninats) {
        List<Students> visi = faili.nolasitVisus();

        Students esosais = visi.stream()
                .filter(s -> s.getPersonasKods().equals(atjauninats.getPersonasKods()))
                .findFirst()
                .orElse(null);

        if (esosais == null)
            throw new IevadesKluda("Students ar so personas kodu nav atrasts.");

        // pārbauda, vai jaunais epasts nav aizņemts citam
        boolean epastsAiznemtsCitam = visi.stream().anyMatch(s ->
                !s.getPersonasKods().equals(atjauninats.getPersonasKods()) &&
                s.getEpasts().equalsIgnoreCase(atjauninats.getEpasts())
        );
        if (epastsAiznemtsCitam)
            throw new IevadesKluda("Epasts aiznemts citam studentam.");

        esosais.setVards(atjauninats.getVards());
        esosais.setUzvards(atjauninats.getUzvards());
        esosais.setEpasts(atjauninats.getEpasts());

        faili.saglabatVisus(visi);
    }
}
