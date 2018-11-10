package modele;

import java.util.ArrayList;
import java.util.List;

public class Carrefour {
    private int id;
    private String libellecarrefour;
    private List<String> identifiantTrancon;
    private double coordX;
    private double coordY;
    private List<Carrefour> carrefoursVoisin;

    public Carrefour(int id, String libellecarrefour, String identifiantTrancon, double coordX, double coordY) {
        this.identifiantTrancon = new ArrayList<String>();
        carrefoursVoisin=new ArrayList<Carrefour>();
        this.id = id;
        this.libellecarrefour = libellecarrefour;
        this.setIdentifiantTrancon(identifiantTrancon);
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibellecarrefour() {
        return libellecarrefour;
    }

    public void setLibellecarrefour(String libellecarrefour) {
        this.libellecarrefour = libellecarrefour;
    }

    public List<String> getIdentifiantTrancon() {
        return identifiantTrancon;
    }

    public void setIdentifiantTrancon(String identifiantTrancon) {
        String[] labels= libellecarrefour.split("|");
        for (String label:labels) {
            this.identifiantTrancon.add(label);
        }
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public void addCarrefoursVoisin( Carrefour c){
        this.carrefoursVoisin.add(c);
    }

    //function that will return the Distance beetween two Intersections
    public double getDistanceBetweenCarrefours(Carrefour c1){


        return 0.0;
    }
}
