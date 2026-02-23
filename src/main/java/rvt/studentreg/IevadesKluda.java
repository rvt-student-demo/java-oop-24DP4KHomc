package rvt.studentreg;

// Paša definēts izņēmums nepareizai lietotāja ievadei.
// Manto no RuntimeException, lai nevajag throws deklarācijas.
public class IevadesKluda extends RuntimeException {
    public IevadesKluda(String zinojums) {
        super(zinojums);
    }
}