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
        int iterationValue = 0;
        Set<Carrefour> carrefoursNonParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.add(source);

        while (carrefoursNonParcourus.size() != 0) {
            Carrefour carrefourCourant = getCarrefourPlusPetiteDistance(carrefoursNonParcourus);
            carrefoursNonParcourus.remove(carrefourCourant);

            for(Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();

                if((distance+carrefourCourant.getDistanceDeLaSource())<carrefourAdjacent.getDistanceDeLaSource()){
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance, algorithmeMethode);
                    carrefoursNonParcourus.add(carrefourAdjacent);
                }
            }
            iterationValue++;
            if(carrefourCourant==fin){
                System.out.println("Intersection passé :"+iterationValue );
                return graphe;
            }
        }
        System.out.println("Intersection passée : "+iterationValue );
        return graphe;
    }

    public ListeCarrefours cheminLePlusCourtAStar(ListeCarrefours graphe, Carrefour source, Carrefour fin, String algorithmeMethode) {
        source.setDistanceDeLaSource(0.0);
        int iterationValue = 0;

        Set<Carrefour> carrefoursNonParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.add(source);

        while (carrefoursNonParcourus.size() != 0) {
            Carrefour carrefourCourant = getCarrefourPlusPetiteDistanceAvecHeuristique(carrefoursNonParcourus,fin);
            carrefoursNonParcourus.remove(carrefourCourant);

            for(Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();

                if((distance+carrefourCourant.getDistanceDeLaSource()+getHeuristique(carrefourAdjacent,fin))<carrefourAdjacent.getDistanceDeLaSource()){
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance, algorithmeMethode);
                    carrefoursNonParcourus.add(carrefourAdjacent);
                }
            }
            iterationValue++;


           if(carrefourCourant==fin){
                System.out.println("Intersection passé :"+iterationValue );
                return graphe;
            }
        }
        System.out.println("Intersection passée : "+iterationValue );
        return graphe;
    }

    public ListeCarrefours cheminLePlusCourtFibonacci(ListeCarrefours graphe, Carrefour source, Carrefour fin, String algorithmeMethode) {
        int iterationValue = 0;

        source.setDistanceDeLaSource(0.0);

        FibonacciHeap<Carrefour> carrefoursNonParcourus = new FibonacciHeap<>();
        Set<Carrefour> carrefoursParcourus = new HashSet<Carrefour>();

        carrefoursNonParcourus.enqueue(source,source.getDistanceDeLaSource());

        while (!carrefoursNonParcourus.isEmpty()) {

            Carrefour carrefourCourant =  carrefoursNonParcourus.dequeueMin().getValue();

            for(Map.Entry<Carrefour, Double> adjacent : carrefourCourant.getCarrefoursAdjacents().entrySet()) {
                Carrefour carrefourAdjacent = adjacent.getKey();
                Double distance = adjacent.getValue();

                if((distance+carrefourCourant.getDistanceDeLaSource())<carrefourAdjacent.getDistanceDeLaSource()){
                    calculerDistanceMinimum(carrefourAdjacent, carrefourCourant, fin, distance, algorithmeMethode);
                    carrefoursNonParcourus.enqueue(carrefourAdjacent,carrefourAdjacent.getDistanceDeLaSource());
                }
            }
            iterationValue++;
            if(carrefourCourant==fin){
                System.out.println("Intersection passé :"+iterationValue );
                return graphe;
            }
        }
        System.out.println("Intersection passée : "+iterationValue );

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

    private Carrefour getCarrefourPlusPetiteDistanceAvecHeuristique(Set <Carrefour> carrefoursNonParcourus, Carrefour fin) {
        Carrefour carrefourPlusPetiteDistance = null;
        double plusPetiteDistance = Double.MAX_VALUE;

        for (Carrefour carrefour: carrefoursNonParcourus) {
            double distanceCarrefour = carrefour.getDistanceDeLaSource()+getHeuristique(fin,carrefour);

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
                carrefourEvalue.setDistanceDeLaSource(distanceCarrefourCourant + distance);
                LinkedList<Carrefour> plusCoursChemin = new LinkedList<Carrefour>(carrefourCourant.getPlusCourtChemin());
                plusCoursChemin.add(carrefourCourant);
                carrefourEvalue.setPlusCourtChemin(plusCoursChemin);
            }

        } else {
            double distancePrediteCarrefourFin = getHeuristique(carrefourEvalue, carrefourFin);

            if (distanceCarrefourCourant + distance  < carrefourEvalue.getDistanceDeLaSource()) {
                carrefourEvalue.setDistanceDeLaSource(distanceCarrefourCourant + distance );
                LinkedList<Carrefour> plusCoursChemin = new LinkedList<Carrefour>(carrefourCourant.getPlusCourtChemin());
                plusCoursChemin.add(carrefourCourant);
                carrefourEvalue.setPlusCourtChemin(plusCoursChemin);
            }
        }


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
