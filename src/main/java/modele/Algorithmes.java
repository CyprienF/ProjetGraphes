package modele;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

public class Algorithmes {

    /**
     * Retourne le chemin le plus court avec l'aglorithme de Djikstra
     *
     *
     * @param graphe
     * @param source
     * @param algorithmeMethode
     * @return
     */
    public ListeCarrefours cheminLePlusCourt(ListeCarrefours graphe, Carrefour source, Carrefour fin, String algorithmeMethode) {
        source.setDistanceDeLaSource(0.0);
        int iterationValue = 0;
        Set<Carrefour> carrefoursNonParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.add(source);

        while (carrefoursNonParcourus.size() != 0) {
            Carrefour carrefourCourant = getCarrefourPlusPetiteDistance(carrefoursNonParcourus);
            carrefoursNonParcourus.remove(carrefourCourant);

            //On parcours tous les carrefours adjacent
            for (Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();

                //Si c'est la premeières fois qu'on visite le carrefours en rentre dans la condition obligadoirement
                //Si ce n'est pas la premeère fois on controle si la nouvelle distance trouvé est inférieur à l'ancienne distance
                if ((distance + carrefourCourant.getDistanceDeLaSource()) < carrefourAdjacent.getDistanceDeLaSource()) {
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance);
                    carrefoursNonParcourus.add(carrefourAdjacent);
                }
            }
            iterationValue++;
            if (carrefourCourant == fin) {
                System.out.println("Intersection passé :" + iterationValue);
                return graphe;
            }
        }
        System.out.println("Intersection passée : " + iterationValue);
        //retourne le graphe de départ aveec la liste des plus court chemin de chaque carrefours
        return graphe;
    }

    /**
     * Retourne le chemin le plus court avec l'aglorithme de A*
     * @param graphe
     * @param source
     * @param fin
     * @param algorithmeMethode
     * @return
     */
    public ListeCarrefours cheminLePlusCourtAStar(ListeCarrefours graphe, Carrefour source, Carrefour fin, String algorithmeMethode) {
        source.setDistanceDeLaSource(0.0);
        int iterationValue = 0;

        Set<Carrefour> carrefoursNonParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.add(source);

        while (carrefoursNonParcourus.size() != 0) {
            Carrefour carrefourCourant = getCarrefourPlusPetiteDistanceAvecHeuristique(carrefoursNonParcourus, fin);
            carrefoursNonParcourus.remove(carrefourCourant);

            //On parcours tous les carrefours adjacent
            for (Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();

                //Si c'est la premeières fois qu'on visite le carrefours en rentre dans la condition obligadoirement
                //Si ce n'est pas la premeère fois on controle si la nouvelle distance trouvé est inférieur à l'ancienne distance
                if ((distance + carrefourCourant.getDistanceDeLaSource() + getHeuristique(carrefourAdjacent, fin)) < carrefourAdjacent.getDistanceDeLaSource()) {
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance);
                    carrefoursNonParcourus.add(carrefourAdjacent);
                }
            }
            iterationValue++;


            if (carrefourCourant == fin) {
                System.out.println("Intersection passé :" + iterationValue);
                return graphe;
            }
        }
        System.out.println("Intersection passée : " + iterationValue);
        //retourne le graphe de départ aveec la liste des plus court chemin de chaque carrefours
        return graphe;
    }

    /**
     *Retourne le chemin le plus court avec l'aglorithme de Dijkstra en utilisant le tas de fibonnaci
     * @param graphe
     * @param source
     * @param fin
     * @param algorithmeMethode
     * @return
     */
    public ListeCarrefours cheminLePlusCourtFibonacci(ListeCarrefours graphe, Carrefour source, Carrefour fin, String algorithmeMethode) {
        int iterationValue = 0;

        source.setDistanceDeLaSource(0.0);

        FibonacciHeap<Carrefour> carrefoursNonParcourus = new FibonacciHeap<>();
        Set<Carrefour> carrefoursParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.enqueue(source, source.getDistanceDeLaSource());

        while (!carrefoursNonParcourus.isEmpty()) {
            Carrefour carrefourCourant = carrefoursNonParcourus.dequeueMin().getValue();

            //On parcours tous les carrefours adjacent
            for (Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();
                //Si c'est la premeières fois qu'on visite le carrefours en rentre dans la condition obligadoirement
                //Si ce n'est pas la premeère fois on controle si la nouvelle distance trouvé est inférieur à l'ancienne distance
                if ((distance + carrefourCourant.getDistanceDeLaSource()) < carrefourAdjacent.getDistanceDeLaSource()) {
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance);
                    carrefoursNonParcourus.enqueue(carrefourAdjacent, carrefourAdjacent.getDistanceDeLaSource());
                }
            }

            iterationValue++;
            if (carrefourCourant == fin) {
                System.out.println("Intersection passé :" + iterationValue);
                return graphe;
            }
        }
        System.out.println("Intersection passée : " + iterationValue);
        //retourne le graphe de départ aveec la liste des plus court chemin de chaque carrefours
        return graphe;
    }


    /**
     * Retourne le carrefour non visité qui a la plus petite distance
     *
     * @param carrefoursNonParcourus
     * @return retourne le carrefour voisin avec la plus petite distance
     */
    private Carrefour getCarrefourPlusPetiteDistance(Set<Carrefour> carrefoursNonParcourus) {
        Carrefour carrefourPlusPetiteDistance = null;
        double plusPetiteDistance = Double.MAX_VALUE;

        for (Carrefour carrefour : carrefoursNonParcourus) {
            double distanceCarrefour = carrefour.getDistanceDeLaSource();

            if (distanceCarrefour < plusPetiteDistance) {
                plusPetiteDistance = distanceCarrefour;
                carrefourPlusPetiteDistance = carrefour;
            }
        }

        return carrefourPlusPetiteDistance;
    }

    /**
     *  Retourne le carrefour non visité qui a la plus petite distance en ajoutant le poids de l'heuristique
     * @param carrefoursNonParcourus
     * @param fin
     * @return le carrefours le plus proche en prennant en compte l'heuristique
     */
    private Carrefour getCarrefourPlusPetiteDistanceAvecHeuristique(Set<Carrefour> carrefoursNonParcourus, Carrefour fin) {
        Carrefour carrefourPlusPetiteDistance = null;
        double plusPetiteDistance = Double.MAX_VALUE;

        for (Carrefour carrefour : carrefoursNonParcourus) {
            double distanceCarrefour = carrefour.getDistanceDeLaSource() + getHeuristique(fin, carrefour);

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
    private void calculerDistanceMinimum(Carrefour carrefourEvalue, Carrefour carrefourCourant, Carrefour carrefourFin, Double distance) {
        Double distanceCarrefourCourant = carrefourCourant.getDistanceDeLaSource();

        if (distanceCarrefourCourant + distance < carrefourEvalue.getDistanceDeLaSource()) {
            carrefourEvalue.setDistanceDeLaSource(distanceCarrefourCourant + distance);
            carrefourEvalue.setDistanceDeLaSource(distanceCarrefourCourant + distance);
            LinkedList<Carrefour> plusCoursChemin = new LinkedList<Carrefour>(carrefourCourant.getPlusCourtChemin());
            plusCoursChemin.add(carrefourCourant);
            carrefourEvalue.setPlusCourtChemin(plusCoursChemin);
        }

    }

    /**
     * @param voisin
     * @param fin
     * @return retourne la distance en mettre en le carrefours voisin et le carrefours fin
     */
    private double getHeuristique(Carrefour voisin, Carrefour fin) {
        return voisin.getDistanceBetweenCarrefours(fin);
    }


}
