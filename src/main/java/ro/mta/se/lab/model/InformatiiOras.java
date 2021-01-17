package ro.mta.se.lab.model;
               public final class InformatiiOras {

                   /**
                    * Aceasta clasa modeleaza interfata, pe baza ei se fac modificari asupra interfeteii
                    */
    private int id;
    private String Nume_oras;
    private double lat;
    private double lon;
    private String Cod;

    public InformatiiOras(int id, String nume_oras, double lat, double lon, String cod) {
        this.id = id;
        Nume_oras = nume_oras;
        this.lat = lat;
        this.lon = lon;
        Cod = cod;
    }

    public int getId() {
        return id;
    }

    public String getNume_oras() {
        return Nume_oras;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCod() {
        return Cod;
    }
}