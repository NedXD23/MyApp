package ro.mta.se.lab.model;

import java.util.Calendar;
public class RaportVreme {

    private double  umitiate;
    private double vant;
    private double temperatura;
    private double temperatura_max;
    private double temperatura_min;
    private double presiune;

    private String vreme;
    private String descriere;
    private String icon;

    private Calendar calendar;



    public RaportVreme(double umitiate, double vant, double temperatura, double temperatura_max, double temperatura_min, double presiune, String vreme, String descriere, String icon, Calendar calendar) {
        this.umitiate = umitiate;
        this.vant = vant;
        this.temperatura = temperatura;
        this.temperatura_max = temperatura_max;
        this.temperatura_min = temperatura_min;
        this.presiune = presiune;
        this.vreme = vreme;
        this.descriere = descriere;
        this.icon = icon;
        this.calendar=calendar;


    }
    public Calendar getCalendar() {
        return calendar;
    }
    public double getUmitiate() {
        return umitiate;
    }

    public double getVant() {
        return vant;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getTemperatura_max() {
        return temperatura_max;
    }

    public double getTemperatura_min() {
        return temperatura_min;
    }

    public double getPresiune() {
        return presiune;
    }

    public String getVreme() {
        return vreme;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getIcon() {
        return icon;
    }





    public void setUmitiate(double umitiate) {
        this.umitiate = umitiate;
    }

    public void setVant(double vant) {
        this.vant = vant;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setTemperatura_max(double temperatura_max) {
        this.temperatura_max = temperatura_max;
    }

    public void setTemperatura_min(double temperatura_min) {
        this.temperatura_min = temperatura_min;
    }

    public void setPresiune(double presiune) {
        this.presiune = presiune;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }




}
