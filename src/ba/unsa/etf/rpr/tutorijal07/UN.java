package ba.unsa.etf.rpr.tutorijal07;

import java.io.Serializable;
import java.util.ArrayList;

public class UN implements Serializable {
    private ArrayList<Drzava> drzave;

    public UN() {
    }

    public ArrayList<Drzava> getDrzave() {
        return drzave;
    }

    public void setDrzave(ArrayList<Drzava> drzave) {
        this.drzave = drzave;
    }
}
