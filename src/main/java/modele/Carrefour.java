package modele;

import java.util.ArrayList;
import java.util.List;

public class Carrefour {
    private int id;
    private double coordX;
    private double coordY;
    private String libelleCarrefour;
    private List<String> identifiantTroncon;
    private List<Carrefour> carrefoursVoisins;

    public Carrefour(int id, double coordX, double coordY, String libelleCarrefour, String identifiantTroncon) {
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.libelleCarrefour = libelleCarrefour;
        this.identifiantTroncon = new ArrayList<String>();
        this.carrefoursVoisins = new ArrayList<Carrefour>();
        this.setIdentifiantTroncon(identifiantTroncon);
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

    public List<String> getIdentifiantTroncon() {
        return identifiantTroncon;
    }

    public void setIdentifiantTroncon(String identifiantTroncon) {
        String[] labels = libelleCarrefour.split("|");

        for (String label : labels) {
            this.identifiantTroncon.add(label);
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

    public void addCarrefourVoisin(Carrefour carrefour){
        this.carrefourVoisin.add(carrefour);
    }

    /**
     * Return the distance beetween two intersections
     *
     * @param c1
     * @return
     */
    public double getDistanceBetweenCarrefours(Carrefour carrefour){

        return 0.0;
    }
}
