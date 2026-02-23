package rvt.studentreg;

import java.util.regex.Pattern;

// Klase, kas pārbauda lietotāja ievadi ar RegEx.
// Ja ievade nav korekta, tiek izmesta IevadesKlauda.
public class Validatori {

    // RegEx: tikai burti, vismaz 3 simboli
    private static final Pattern VARDS_UZVARDS =
            Pattern.compile("^[A-Za-z]{3,}$");

    // RegEx e-pasta formātam
    private static final Pattern EPASTS =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // RegEx personas kodam: 010101-12345
    private static final Pattern PERSONAS_KODS =
            Pattern.compile("^\\d{6}-\\d{5}$");
 
    public static void parbaudiVardu(String vards) {
        if (vards == null || vards.trim().isEmpty())
            throw new IevadesKlauda("Vards nedrikst but tukss.");

        if (!VARDS_UZVARDS.matcher(vards.trim()).matches())
            throw new IevadesKlauda("Vards: tikai burti, min 3 simboli.");
    }

    public static void parbaudiUzvardu(String uzvards) {
        if (uzvards == null || uzvards.trim().isEmpty())
            throw new IevadesKlauda("Uzvards nedrikst but tukss.");

        if (!VARDS_UZVARDS.matcher(uzvards.trim()).matches())
            throw new IevadesKlauda("Uzvards: tikai burti, min 3 simboli.");
    }

    public static void parbaudiEpastu(String epasts) {
        if (epasts == null || epasts.trim().isEmpty())
            throw new IevadesKlauda("Epasts nedrikst but tukss.");

        if (!EPASTS.matcher(epasts.trim()).matches())
            throw new IevadesKlauda("Nederigs epasta formats.");
    }

    public static void parbaudiPersonasKodu(String personasKods) {
        if (personasKods == null || personasKods.trim().isEmpty())
            throw new IevadesKlauda("Personas kods nedrikst but tukss.");

        if (!PERSONAS_KODS.matcher(personasKods.trim()).matches())
            throw new IevadesKlauda(
                    "Nederigs personas koda formats. Piemers: 010101-12345");
    }
}