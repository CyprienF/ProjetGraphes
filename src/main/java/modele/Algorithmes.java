package modele;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Algorithmes {

    /**
     *
     *
     * @param graphe
     * @param source
     * @return
     */
    public ListeCarrefours Dijkstra(ListeCarrefours graphe, Carrefour source) {
        source.setDistanceDeLaSource(0);

        Set<Carrefour> carrefoursParcourus = new HashSet<Carrefour>();
        Set<Carrefour> carrefoursNonParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.add(source);

        while (carrefoursNonParcourus.size() != 0) {
            Carrefour carrefourCourant = getPlusPetiteDistance(carrefoursNonParcourus);
            carrefoursNonParcourus.add(carrefourCourant);

            for(Map.Entry<Carrefour, Integer> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Integer distance = adjacent.getValue();

                if (!carrefoursParcourus.contains(carrefourAdjacent)) {
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, distance);
                    carrefoursNonParcourus.add(carrefourAdjacent);
                }
            }
        }

        return graphe;
    }

    /**
     * Retourne le carrefour qui a la plus petite distance par rapport aux carrefours non parcourus
     *
     * @param carrefoursNonParcourus
     * @return
     */
    private Carrefour getPlusPetiteDistance(Set <Carrefour> carrefoursNonParcourus) {
        Carrefour carrefourPlusPetiteDistance = null;
        int plusPetiteDistance = Integer.MAX_VALUE;

        for (Carrefour carrefour: carrefoursNonParcourus) {
            int distanceCarrefour = carrefour.getDistanceDeLaSource();

            if (distanceCarrefour < plusPetiteDistance) {
                plusPetiteDistance = distanceCarrefour;
                carrefourPlusPetiteDistance = carrefour;
            }
        }

        return carrefourPlusPetiteDistance;
    }

    /**
     *
     *
     * @param carrefourEvalue
     * @param carrefourSource
     * @param distance
     */
    private void calculerDistanceMinimum(Carrefour carrefourEvalue, Carrefour carrefourSource, Integer distance) {
        Integer distanceSource = carrefourSource.getDistanceDeLaSource();

        if (distanceSource + distance < carrefourSource.getDistanceDeLaSource()) {
            carrefourEvalue.setDistanceDeLaSource(distanceSource + distance);
            LinkedList<Carrefour> plusCoursChemin = new LinkedList<Carrefour>(carrefourSource.getPlusCourtChemin());
            plusCoursChemin.add(carrefourSource);
            carrefourEvalue.setPlusCourtChemin(plusCoursChemin);
        }
    }
}
