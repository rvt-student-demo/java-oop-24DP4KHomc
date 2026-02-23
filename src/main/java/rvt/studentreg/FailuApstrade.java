package rvt.studentreg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Atbild par darbu ar CSV failu:
// - izveido failu
// - nolasa studentus
// - saglabā studentus
public class FailuApstrade {

    private final Path csvCels;

    public FailuApstrade(String fails) { 
        this.csvCels = Paths.get(fails);
        nodrosiniFailu();
    }

    // Ja fails neeksistē — izveido ar header
    private void nodrosiniFailu() {
        try {
            if (!Files.exists(csvCels)) {
                Path parent = csvCels.getParent();
                if (parent != null && !Files.exists(parent))
                    Files.createDirectories(parent);

                Files.createFile(csvCels);
                Files.writeString(csvCels,
                        "Vards,Uzvards,Epasts,PersonasKods,RegistresanasDatumsLaiks\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Neizdevas izveidot CSV failu: " + e.getMessage());
        }
    }

    
    // Nolasa visus studentus no CSV
    public List<Students> nolasitVisus() {
        List<Students> saraksts = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvCels)) {
            String rinda;
            boolean pirma = true;

            while ((rinda = br.readLine()) != null) {
                if (pirma) { pirma = false; continue; } // izlaiž header
                if (rinda.trim().isEmpty()) continue;

                Students s = Students.noCsvRindas(rinda);
                if (s != null) saraksts.add(s);
            }
        } catch (IOException e) {
            throw new RuntimeException("Neizdevas nolasit CSV: " + e.getMessage());
        }

        return saraksts;
    }

    // Pārraksta visu CSV failu ar jauno sarakstu
    public void saglabatVisus(List<Students> studenti) {
        try (BufferedWriter bw = Files.newBufferedWriter(csvCels)) {
            bw.write("Vards,Uzvards,Epasts,PersonasKods,RegistresanasDatumsLaiks");
            bw.newLine();

            for (Students s : studenti) {
                bw.write(s.uzCsvRindu());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Neizdevas saglabat CSV: " + e.getMessage());
        }
    }
}