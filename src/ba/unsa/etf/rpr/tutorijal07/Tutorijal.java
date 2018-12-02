package ba.unsa.etf.rpr.tutorijal07;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public ArrayList<Grad> ucitajGradove(){
        ArrayList<Grad> gradovi= new ArrayList<>();
        Scanner citac;
        try {
            citac = new Scanner(new FileReader("mjerenja.txt"));
        }
        catch (IOException e){
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne može otvoriti");
            System.out.println("Greška: "+ e);
            return gradovi;
        }
        try{
            while(citac.hasNext()){
               String[] s = citac.nextLine().split(",");
               Grad grad= new Grad();
               grad.setNaziv(s[0]);
               double[] niz = new double [s.length-1];
               for(int i=1; i<1000; i++){
                   if(i==s.length) break;
                   niz[i-1] = Double.parseDouble(s[i]);
               }
               grad.setTemperature(niz);
               gradovi.add(grad);
            }

        }
        finally{ citac.close(); }

        return gradovi;
    }

    public UN ucitajXml(){
        UN un= new UN();
        Document xmldoc= null;
        try{
            DocumentBuilder docReader= DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc=docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
        }
        return un;
    }

    public static void main(String[] args) {
        Tutorijal t= new Tutorijal();
        t.ucitajGradove();
    }
}
