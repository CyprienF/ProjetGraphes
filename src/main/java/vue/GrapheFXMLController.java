package vue;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modele.Algorithmes;
import modele.Carrefour;
import modele.ListeCarrefours;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;

public class GrapheFXMLController implements Initializable, MapComponentInitializedListener {

    @FXML
    protected GoogleMapView mapView;

    private GoogleMap map;

    private ListeCarrefours listeCarrefours;

    private Carrefour selectedCarrefour1;

    private Carrefour selectedCarrefour2;

    private int numberOfSelectedCarrefours;

    private LatLong carrefourLocation;

    private Marker markerCarrefour;

    private MarkerOptions markerOptionsCarrefour;

    private Algorithmes algorithmes = new Algorithmes();

    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    private void initializeAllCarrefours() {
        this.listeCarrefours = new ListeCarrefours();
        this.listeCarrefours.initialisationListeCarrefours();
    }

    public void mapInitialized() {
        this.initializeAllCarrefours();

        LatLong lyonLocation = new LatLong(45.764043, 4.835658999999964);

        // Options de la carte
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(lyonLocation)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(16);

        map = mapView.createMap(mapOptions);

        this.initializeAllMarkers(this.listeCarrefours);
    }

    private void launchAlgorithme(String methode) {
        this.deleteAllMarkers();

        ListeCarrefours listeCarrefoursAlgorithme = algorithmes.cheminLePlusCourt(this.listeCarrefours, selectedCarrefour1, selectedCarrefour2, methode);
        this.initializeAlgorithmeMarkers(listeCarrefoursAlgorithme);
    }

    private void deleteAllMarkers() {
        this.map.clearMarkers();
    }

    private void initializeAllMarkers(ListeCarrefours carrefours) {
        for(Carrefour carrefour : carrefours.getMesCarrefours()) {

            // Position du carrefour
            this.carrefourLocation = new LatLong(carrefour.getCoordY(), carrefour.getCoordX());

            // Ajout du marker sur la carte
            this.markerOptionsCarrefour = new MarkerOptions();
            this.markerOptionsCarrefour.position(this.carrefourLocation);

            this.markerCarrefour = new Marker(this.markerOptionsCarrefour);

            this.map.addMarker(this.markerCarrefour);

            this.map.addUIEventHandler(this.markerCarrefour, UIEventType.click, (JSObject obj) -> {

                if(this.numberOfSelectedCarrefours == 0) {
                    this.selectedCarrefour1 = carrefour;
                }

                if(this.numberOfSelectedCarrefours == 1) {
                    this.selectedCarrefour2 = carrefour;
                }

                numberOfSelectedCarrefours++;

                System.out.println(selectedCarrefour1);
                System.out.println(selectedCarrefour2);
            });
        }
    }

    private void initializeAlgorithmeMarkers(ListeCarrefours carrefours) {
        for(Carrefour carrefour : carrefours.getMesCarrefours()) {

            // Position du carrefour
            this.carrefourLocation = new LatLong(carrefour.getCoordY(), carrefour.getCoordX());

            // Ajout du marker sur la carte
            this.markerOptionsCarrefour = new MarkerOptions();
            this.markerOptionsCarrefour.position(this.carrefourLocation);

            this.markerCarrefour = new Marker(this.markerOptionsCarrefour);

            this.map.addMarker(this.markerCarrefour);
        }
    }
}
