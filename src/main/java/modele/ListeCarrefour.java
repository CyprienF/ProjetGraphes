package modele;

import java.util.ArrayList;
import java.util.List;

public class ListeCarrefour {
    private List<Carrefour> mesCarrefours;

    public ListeCarrefour() {
        this.mesCarrefours = new ArrayList<Carrefour>();
    }

    /**
     * Read file and save all the intersections
     *
     * @return
     */
    public boolean initialisationListeCarrefours() {

        return true;
    }

    /**
     * Read list to set the relation between intersections
     *
     * @return
     */
    public boolean creationMatriceAdjacence() {

        return true;
    }
}
