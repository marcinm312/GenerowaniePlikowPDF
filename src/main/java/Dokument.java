public class Dokument {
    private final String tytul;
    private final String tresc;

    public Dokument(String tytul, String tresc) {
        this.tytul = tytul;
        this.tresc = tresc;
    }

    public String getTytul() {
        return tytul;
    }

    public String getTresc() {
        return tresc;
    }
}