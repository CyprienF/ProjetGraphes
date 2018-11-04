package modele;

import java.util.*;

public class Carrefour {
    private int id;
    private double coordX;
    private double coordY;

    private List<Carrefour> plusCourtChemin = new LinkedList<Carrefour>();
    private Integer distanceDeLaSource = Integer.MAX_VALUE;
    private Map<Carrefour, Integer> carrefoursAdjacents = new HashMap<Carrefour, Integer>();

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

    public void ajouterCarrefourAdjacent(Carrefour voisin, int distance) {
        this.carrefoursAdjacents.put(voisin, distance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibellecarrefour() {
        return libelleCarrefour;
    }

    public void setLibellecarrefour(String libellecarrefour) {
        this.libelleCarrefour = libellecarrefour;
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
        this.carrefoursVoisins.add(carrefour);
    }

    public List<Carrefour> getPlusCourtChemin() {
        return plusCourtChemin;
    }

    public void setPlusCourtChemin(List<Carrefour> plusCourtChemin) {
        this.plusCourtChemin = plusCourtChemin;
    }

    public Integer getDistanceDeLaSource() {
        return distanceDeLaSource;
    }

    public void setDistanceDeLaSource(Integer distanceDeLaSource) {
        this.distanceDeLaSource = distanceDeLaSource;
    }

    public Map<Carrefour, Integer> getCarrefoursAdjacents() {
        return carrefoursAdjacents;
    }

    public void setCarrefoursAdjacents(Map<Carrefour, Integer> carrefoursAdjacents) {
        this.carrefoursAdjacents = carrefoursAdjacents;
    }

    /**
     * Return the distance beetween two intersections
     *
     * @param carrefour
     * @return
     */
    public double getDistanceBetweenCarrefours(Carrefour carrefour){
        return 0.0;
    }
}
