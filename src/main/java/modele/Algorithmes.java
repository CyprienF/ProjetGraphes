package modele;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

public class Algorithmes {

    /**
     * Retourne le chemin le plus court
     *
     * @param graphe
     * @param source
     * @param algorithmeMethode
     * @return
     */
    public ListeCarrefours cheminLePlusCourt(ListeCarrefours graphe, Carrefour source, Carrefour fin, String algorithmeMethode) {
        source.setDistanceDeLaSource(0.0);

        Set<Carrefour> carrefoursParcourus = new HashSet<Carrefour>();
        Set<Carrefour> carrefoursNonParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.add(source);

        while (carrefoursNonParcourus.size() != 0) {
            Carrefour carrefourCourant = getCarrefourPlusPetiteDistance(carrefoursNonParcourus);
            carrefoursNonParcourus.remove(carrefourCourant);

            for(Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();

                if (!carrefoursParcourus.contains(carrefourAdjacent)) {
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance, algorithmeMethode);
                    carrefoursNonParcourus.add(carrefourAdjacent);
                }
            }
            carrefoursParcourus.add(carrefourCourant);
        }

        return graphe;
    }

    /**
     * Retourne le carrefour non visité qui a la plus petite distance
     *
     * @param carrefoursNonParcourus
     * @return
     */
    private Carrefour getCarrefourPlusPetiteDistance(Set <Carrefour> carrefoursNonParcourus) {
        Carrefour carrefourPlusPetiteDistance = null;
        double plusPetiteDistance = Double.MAX_VALUE;

        for (Carrefour carrefour: carrefoursNonParcourus) {
            double distanceCarrefour = carrefour.getDistanceDeLaSource();

            if (distanceCarrefour < plusPetiteDistance) {
                plusPetiteDistance = distanceCarrefour;
                carrefourPlusPetiteDistance = carrefour;
            }
        }

        return carrefourPlusPetiteDistance;
    }

    /**
     * Compare la distance actuelle avec la nouvelle
     *
     * @param carrefourEvalue
     * @param carrefourCourant
     * @param distance
     */
    private void calculerDistanceMinimum(Carrefour carrefourEvalue, Carrefour carrefourCourant, Carrefour carrefourFin, Double distance, String algorithmeMethode) {
        Double distanceCarrefourCourant = carrefourCourant.getDistanceDeLaSource();

        if(algorithmeMethode.equals("Dijkstra")) {

            if (distanceCarrefourCourant + distance < carrefourEvalue.getDistanceDeLaSource()) {
                carrefourEvalue.setDistanceDeLaSource(distanceCarrefourCourant + distance);
            }

        } else {
            double distancePrediteCarrefourFin = getHeuristique(carrefourCourant, carrefourFin);

            if (distanceCarrefourCourant + distance + distancePrediteCarrefourFin < carrefourEvalue.getDistanceDeLaSource()) {
                carrefourEvalue.setDistanceDeLaSource(distanceCarrefourCourant + distance + distancePrediteCarrefourFin);
            }
        }

        LinkedList<Carrefour> plusCoursChemin = new LinkedList<Carrefour>(carrefourCourant.getPlusCourtChemin());
        plusCoursChemin.add(carrefourCourant);
        carrefourEvalue.setPlusCourtChemin(plusCoursChemin);
    }

    /**
     *
     * @param voisin
     * @param fin
     * @return retourne la distance en mettre en le carrefours voisin et le carrefours fin
     */
    private double getHeuristique(Carrefour voisin, Carrefour fin) {
        return voisin.getDistanceBetweenCarrefours(fin);
    }
}
