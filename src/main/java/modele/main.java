package modele;

public class main {
    public static void main(String[] args) {
        ListeCarrefours L1= new ListeCarrefours();
        long start = System.currentTimeMillis();
        L1.initialisationListeCarrefours();


        L1.creationMatriceAdjacence();
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("Premiere init: "+elapsedTimeMillis/1000F);
        start = System.currentTimeMillis();
        //Copie de L1 vers L2
        ListeCarrefours L2 = new ListeCarrefours(L1);
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("Deuxieme init: "+elapsedTimeMillis/1000F);

        System.out.println(L1);
        System.out.println(L2);
    }
}
