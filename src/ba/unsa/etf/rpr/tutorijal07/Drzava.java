package ba.unsa.etf.rpr.tutorijal07;

public class Drzava {
    private String naziv;
    private int brojStanovnika;
    private double[] temperature = new double[1000];
    private Grad glavniGrad;
    private double povrsina;
    private String jedinicaZaPovrsinu;

    public Drzava() {
    }

    public String getNaziv() {
        return naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }
}
