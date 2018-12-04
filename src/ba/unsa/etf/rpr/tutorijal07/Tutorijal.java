package ba.unsa.etf.rpr.tutorijal07;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Scanner citac;
        try {
            citac = new Scanner(new FileReader("mjerenja.txt"));
        } catch (IOException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne može otvoriti");
            System.out.println("Greška: " + e);
            return gradovi;
        }
        try {
            while (citac.hasNext()) {
                String[] s = citac.nextLine().split(",");
                Grad grad = new Grad();
                grad.setNaziv(s[0]);
                double[] niz = new double[s.length - 1];
                for (int i = 1; i < 1000; i++) {
                    if (i == s.length) break;
                    niz[i - 1] = Double.parseDouble(s[i]);
                }
                grad.setTemperature(niz);
                gradovi.add(grad);
            }

        } finally {
            citac.close();
        }

        return gradovi;
    }

    public static UN ucitajXml(ArrayList<Grad> gradovi) {
        UN un = new UN();
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
        }

        ArrayList<Drzava> spisakDrzava = new ArrayList<>();

        Element element = null;
        if (xmldoc != null) {
            element = xmldoc.getDocumentElement();
        }
        NodeList drzave = element != null ? element.getChildNodes() : null;

        if (drzave != null) {
            for (int i = 0; i < drzave.getLength(); i++) {
                Node drzava = drzave.item(i);
                Drzava novaDrzava = new Drzava(); //napravljena nova drzava koja ce se dodati u listu

                if (drzava instanceof Element) {
                    Element e = (Element) drzava;
                    novaDrzava.setBrojStanovnika(Integer.parseInt(e.getAttribute("stanovnika"))); //postavljanje broja stanovnika za novu drzavu

                    NodeList podaci = e.getChildNodes(); //uzimamo ostale podatke

                    label:
                    for (int j = 0; j < podaci.getLength(); j++) {
                        Node podatak = podaci.item(j);

                        if (podatak instanceof Element) {
                         //   Element trenutniPodatak = (Element) podatak;

                            String imePodatka = podatak.getTextContent();

                            switch (imePodatka) {
                                case "naziv":
                                    novaDrzava.setNaziv("???");
                                    break label;
                                case "glavnigrad":
                                    Grad glavniGrad = new Grad();
                                    glavniGrad.setBrojStanovnika(Integer.parseInt(((Element)podatak).getAttribute("stanovnika")));
                                    NodeList podaciGrad = (podatak).getChildNodes();
                                    for (int k = 0; k < podaciGrad.getLength(); k++) {
                                        Node podatakGrad = podaciGrad.item(k);
                                        if (podatakGrad instanceof Element) {
                                            if (((Element) podaciGrad).getTagName().equals("naziv")) {
                                                glavniGrad.setNaziv(((Element) podaciGrad).getTextContent());
                                                boolean mjerenja = false;
                                                for (var x : gradovi) {
                                                    if (glavniGrad.getNaziv().equals(x.getNaziv())) {
                                                        glavniGrad.setTemperature((x.getTemperature()));
                                                        mjerenja = true;
                                                    }
                                                }
                                                if (!mjerenja) {
                                                    glavniGrad.setTemperature(null);
                                                }
                                                novaDrzava.setGlavniGrad(glavniGrad);
                                            }
                                        }
                                    }
                                    break;
                                case "povrsina":
                                    novaDrzava.setJedinicaZaPovrsinu(((Element)podatak).getAttribute("jedinica"));
                                    novaDrzava.setPovrsina(Double.parseDouble(((Element)podatak).getTextContent()));
                                    break;
                            }
                        }

                    }
                    spisakDrzava.add(novaDrzava);
                    }

                }
        }
        un.setDrzave(spisakDrzava);
        return un;
    }

    public static void ispisiXml(UN un) {
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
            izlaz.writeObject(un);
            izlaz.close();
        } catch (Exception e) {
            System.out.println("Greška: " + e);
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        Tutorijal t = new Tutorijal();
        UN un= new UN();
        un= ucitajXml(ucitajGradove());
        ispisiXml(un);
    }

}
