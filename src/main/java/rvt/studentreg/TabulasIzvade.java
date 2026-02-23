package rvt.studentreg;

import java.util.List;

// Atbild par skaistu ASCII tabulas izvadi konsolē
public class TabulasIzvade {

    public static void paraditStudentus(List<Students> studenti) {
        String[] virsraksti =
                {"Vards", "Uzvards", "Epasts", "PersonasKods", "RegDatLaiks"};
        int[] platumi = {12, 14, 28, 14, 19};

        linija(platumi);
        rinda(virsraksti, platumi);
        linija(platumi);

        if (studenti.isEmpty()) {
            rinda(new String[]{"(nav ierakstu)", "", "", "", ""}, platumi);
        } else {
            for (Students s : studenti) {
                rinda(new String[]{
                        s.getVards(),
                        s.getUzvards(),
                        s.getEpasts(),
                        s.getPersonasKods(),
                        s.getRegistresanasDatumsLaiks()
                }, platumi);
            }
        } 

        linija(platumi);
    }

    // Zīmē +-----+ līniju
    private static void linija(int[] platumi) {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int p : platumi) {
            sb.append("-".repeat(p + 2)).append("+");
        }
        System.out.println(sb);
    }

    // Izvada vienu tabulas rindu
    private static void rinda(String[] kolonnas, int[] platumi) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");

        for (int i = 0; i < platumi.length; i++) {
            String teksts = (kolonnas[i] == null) ? "" : kolonnas[i];
            teksts = nogriezt(teksts, platumi[i]);
            sb.append(" ")
              .append(papildinatPaLabi(teksts, platumi[i]))
              .append(" |");
        }
        System.out.println(sb);
    }

    private static String papildinatPaLabi(String s, int platums) {
        if (s.length() >= platums) return s;
        return s + " ".repeat(platums - s.length());
    }

    // Ja teksts par garu — nogriež un pieliek ...
    private static String nogriezt(String s, int platums) {
        if (s.length() <= platums) return s;
        return s.substring(0, Math.max(0, platums - 3)) + "...";
    }
}
