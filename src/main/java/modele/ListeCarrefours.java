package modele;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ListeCarrefours {
    private List<Carrefour> mesCarrefours;

    public ListeCarrefours() {
        this.mesCarrefours = new ArrayList<Carrefour>();
    }

    public ListeCarrefours(ListeCarrefours listeCarrefours) {
        this.mesCarrefours = new ArrayList<Carrefour>();
        for (Carrefour carrefour : listeCarrefours.getMesCarrefours()) {
            this.mesCarrefours.add(new Carrefour(carrefour));
        }
        this.creationMatriceAdjacence();
    }

    /**
     * Read file and save all the intersections
     *
     * @return
     */
    public boolean initialisationListeCarrefours() {
        String filename = "./src/main/resources/adr_voie_lieu.adrcarrefour.json";
        try {
            Object obj = new JSONParser().parse(new FileReader(filename));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray listeCareffours = (JSONArray) jsonObject.get("features");
            JSONObject carrefour;
            JSONObject properties;
            JSONObject geometry;
            JSONArray coordinates;
            Carrefour c;
            for (int i = 0; i < listeCareffours.size(); i++) {
                carrefour = (JSONObject) listeCareffours.get(i);
                properties = (JSONObject) carrefour.get("properties");
                geometry = (JSONObject) carrefour.get("geometry");
                coordinates = (JSONArray) geometry.get("coordinates");
                c = new Carrefour(i, (Double) coordinates.get(0), (Double) coordinates.get(1), (String) properties.get("libellecarrefour"), (String) properties.get("identtroncon"));
                mesCarrefours.add(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Read list to set the relation between intersections
     *
     * @return
     */
    public boolean creationMatriceAdjacence() {
        for (Carrefour carrefour : mesCarrefours) {
            carrefour.ajoutCarrefoursVoisins(mesCarrefours);
        }

        return true;
    }

    public List<Carrefour> getMesCarrefours() {
        return mesCarrefours;
    }

    public boolean clearCarrefourList() {
        this.mesCarrefours.clear();
        return true;
    }


}
