package ba.unsa.etf.rpr.tutorijal07;

import java.io.Serializable;

public class Grad implements Serializable {
    private String naziv;
    private int brojStanovnika;
    private double povrsina;
    private double[] temperature = new double[1000];

    public Grad() {
        this.naziv = "";
        this.brojStanovnika = 0;
        this.povrsina = 0;
        this.temperature = new double[1000];
    }

    public String getNaziv() {
        return naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public double getPovrsina() {
        return povrsina;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public void setPovrsina(double povrsina) {
        this.povrsina = povrsina;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }
}
