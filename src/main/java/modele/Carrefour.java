package modele;

import java.util.*;

public class Carrefour {
    private int id;
    private double coordX;
    private double coordY;

    private List<Carrefour> plusCourtChemin = new LinkedList<Carrefour>();
    private Double distanceDeLaSource = Double.MAX_VALUE;
    private Map<Carrefour, Double> carrefoursAdjacents;

    private String libelleCarrefour;
    private List<String> identifiantTroncon;

    public Carrefour(int id, double coordX, double coordY, String libelleCarrefour, String identifiantTroncon) {
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.libelleCarrefour = libelleCarrefour;
        this.identifiantTroncon = new ArrayList<String>();
        this.carrefoursAdjacents = new HashMap<Carrefour,Double>();
        this.setIdentifiantTroncon(identifiantTroncon);
    }

    public void ajouterCarrefourAdjacent(Carrefour voisin) {
        this.carrefoursAdjacents.put(voisin, this.getDistanceBetweenCarrefours(voisin));
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
                this.ajouterCarrefourAdjacent(carrefour);
                return true;
            }
        }
        return false;
    }

    public boolean ajoutCarrefoursVoisins (List<Carrefour> mesCarrefours) {
        for (Carrefour carrefour : mesCarrefours) {
            if(carrefour.getId()!= this.id){
                if(!this.carrefoursAdjacents.containsKey(carrefour)){
                    if(carrefour.findTroncon(this)){
                        this.ajouterCarrefourAdjacent(carrefour);
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
