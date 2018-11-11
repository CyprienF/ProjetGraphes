package modele;

public class main {
    public static void main(String[] args) {
        ListeCarrefours L1= new ListeCarrefours();
        L1.initialisationListeCarrefours();
        long start = System.currentTimeMillis();

        L1.creationMatriceAdjacence();
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis/1000F);
        System.out.println(L1);
    }
}
