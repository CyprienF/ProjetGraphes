package modele;

import java.util.*;

public class Carrefour {
    private int id;
    private double coordX;
    private double coordY;

    private List<Carrefour> plusCourtChemin = new LinkedList<Carrefour>();
    private Double distanceDeLaSource = Double.MAX_VALUE;
    private Map<Carrefour, Double> carrefoursAdjacents = new HashMap<Carrefour, Double>();

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

    public void ajouterCarrefourAdjacent(Carrefour voisin, double distance) {
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
        String[] labels = identifiantTroncon.split("\\|");

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

    public List<Carrefour> getCarrefoursVoisins() {
        return carrefoursVoisins;
    }

    public List<Carrefour> getPlusCourtChemin() {
        return plusCourtChemin;
    }

    public void setPlusCourtChemin(List<Carrefour> plusCourtChemin) {
        this.plusCourtChemin = plusCourtChemin;
    }

    public Double getDistanceDeLaSource() {
        return distanceDeLaSource;
    }

    public void setDistanceDeLaSource(Double distanceDeLaSource) {
        this.distanceDeLaSource = distanceDeLaSource;
    }

    public Map<Carrefour, Double> getCarrefoursAdjacents() {
        return carrefoursAdjacents;
    }

    public void setCarrefoursAdjacents(Map<Carrefour, Double> carrefoursAdjacents) {
        this.carrefoursAdjacents = carrefoursAdjacents;
    }

    /**
     * Return the distance beetween two intersections
     *
     * @param carrefour
     * @return
     */
    public double getDistanceBetweenCarrefours(Carrefour carrefour) {
        return 0.0;
    }

    /**
     *
     * @param carrefour
     * Si on trouve un des troncon recherché dans le trancon carrefour actuel on ajoute le carrefour passé en paramtre
     * à l'objet actuel ensuite en retourne true
     * @return
     */
    public boolean findTroncon(Carrefour carrefour) {

        for (String troncon : carrefour.getIdentifiantTroncon()) {
            if(this.identifiantTroncon.contains(troncon)){
                this.addCarrefourVoisin(carrefour);
                return true;
            }
        }
        return false;
    }

<<<<<<< HEAD
    public boolean ajoutCarrefoursVoisins(List<Carrefour> mesCarrefours) {
=======
    public boolean ajoutCarrefoursVoisins (List<Carrefour> mesCarrefours) {
>>>>>>> 2e4de21a3fd39fb5f2550b169da3288c5771441c
        for (Carrefour carrefour : mesCarrefours) {
            if(carrefour.getId()!= this.id){
                if(!this.carrefoursVoisins.contains(carrefour)){
                    if(carrefour.findTroncon(this)){
                        this.carrefoursVoisins.add(carrefour);
                    }
                }

            }
        }

        return true;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Carrefour)) return false;
        Carrefour carrefour = (Carrefour) other;
        return carrefour.getId()== this.id;
    }
}
