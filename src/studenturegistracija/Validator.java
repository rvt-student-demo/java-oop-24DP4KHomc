package studenturegistracija;

public class Validator {
    
    public static boolean parbauditVardu(String vards) {
        return vards.matches("[A-Za-z]{3,}");
        // {3,} = vismaz 3 simboli
        // [A-Za-z] = tikai burti
    }

    public static boolean parbauditPersonasKodu(String Kods) {
        return kods.matches("[0-9]{12}");
        // [0,9] = skaitļi no 0 lidz 9
        // {11} = jābut vismaz 12 simboliem
    }

    public static boolean parbauditEpastu(String epasts) {
        return epasts.contains("@") && epasts.contains(".");
        // ("@") = epasts satur "@" simbolu
        // (".") = epasts satur "." simbolu
    }
}
