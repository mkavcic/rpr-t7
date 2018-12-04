package ba.unsa.etf.rpr.tutorijal07;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

        Element element = xmldoc.getDocumentElement();
        NodeList drzave = element.getChildNodes();

        for (int i = 0; i < drzave.getLength(); i++) {
            Node drzava = drzave.item(i);
            Drzava novaDrzava = new Drzava(); //napravljena nova drzava koja ce se dodati u istu

            if (drzava instanceof Element) {
                Element e = (Element) drzava;
                novaDrzava.setBrojStanovnika(Integer.parseInt(e.getAttribute("stanovnika"))); //postavljanje broja stanovnika za novu drzavu

                NodeList podaci = e.getChildNodes(); //uzimamo ostale podatke

                for (int j = 0; j < podaci.getLength(); j++) {
                    Node podatak = podaci.item(j);

                    if (podatak instanceof Element) {
                        Element trenutniPodatak = (Element) podatak;

                        String imePodatka = trenutniPodatak.getTextContent();

                        if (imePodatka == "naziv") novaDrzava.setNaziv(imePodatka);
                        else if (imePodatka == "glavnigrad") {
                            Grad glavniGrad = new Grad();
                            glavniGrad.setBrojStanovnika(Integer.parseInt(trenutniPodatak.getAttribute("stanovnika")));
                            NodeList podaciGrad = trenutniPodatak.getChildNodes();
                            for (int k = 0; k < podaciGrad.getLength(); k++) {
                                Node podatakGrad = podaciGrad.item(k);
                                if (podaciGrad instanceof Element) {
                                    if (((Element) podaciGrad).getTagName().equals("naziv")) {
                                        glavniGrad.setNaziv(((Element) podaciGrad).getTextContent());
                                        boolean mjerenja = false;
                                        for (var x : gradovi) {
                                            if (glavniGrad.getNaziv().equals(x.getNaziv())) {
                                                glavniGrad.setTemperature((x.getTemperature()));
                                                mjerenja = true;
                                            }
                                        }
                                        // if(!mjerenja){ glavniGrad.setTemperature();}
                                        novaDrzava.setGlavniGrad(glavniGrad);
                                    }
                                }
                            }
                            if (imePodatka == "povrsina") {
                                novaDrzava.setJedinicaZaPovrsinu(trenutniPodatak.getAttribute("jedinica"));
                                novaDrzava.setPovrsina(Double.parseDouble(trenutniPodatak.getTextContent()));
                            }
                        }
                    }
                    spisakDrzava.add(novaDrzava);
                }
            }
            un.setDrzave(spisakDrzava);
        }
        return un;
    }

    public static void main(String[] args) {
        Tutorijal t = new Tutorijal();
        t.ucitajGradove();
        t.ucitajXml(null);
    }
}
