package vue;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modele.Algorithmes;
import modele.Carrefour;
import modele.ListeCarrefours;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class
GrapheFXMLController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML
    protected GoogleMapView mapView;

    private GoogleMap map;

    protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;
    protected DirectionsRenderer directionsRenderer = null;

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
        long start = System.currentTimeMillis();
        this.listeCarrefours.initialisationListeCarrefours();
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("Lecture Fichier: "+elapsedTimeMillis/1000F);
        start = System.currentTimeMillis();
        this.listeCarrefours.creationMatriceAdjacence();
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("Matrice: "+elapsedTimeMillis/1000F);
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
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

        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
    }

    private void createRoute() {

        //List<Carrefour> mesCarrefours = listeCarrefours.getMesCarrefours();

        //Carrefour origin = mesCarrefours.get(0);
        //Carrefour fin = mesCarrefours.get(mesCarrefours.size() - 1);

        LatLong originLatLong = new LatLong(selectedCarrefour1.getCoordX(), selectedCarrefour1.getCoordY());
        LatLong finLatLong = new LatLong(selectedCarrefour2.getCoordX(), selectedCarrefour2.getCoordY());

        DirectionsRequest request = new DirectionsRequest("Lyon", "Paris", TravelModes.WALKING);
        directionsService.getRoute(request, this, new DirectionsRenderer(false, mapView.getMap(), directionsPane));
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

                    this.createRoute();
                }

                numberOfSelectedCarrefours++;

                System.out.println(selectedCarrefour1);
                System.out.println(selectedCarrefour2);
            });
        }
    }
}
