package rvt.studentreg;

// Paša definēts izņēmums nepareizai lietotāja ievadei.
// Manto no RuntimeException, lai nevajag throws deklarācijas.
public class IevadesKlauda extends RuntimeException {
    public IevadesKlauda(String zinjojums) {
        super(zinjojums);
    }
} 