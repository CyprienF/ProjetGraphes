package vue;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modele.Carrefour;
import modele.ListeCarrefours;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;

public class GrapheFXMLController implements Initializable, MapComponentInitializedListener {

    @FXML
    protected GoogleMapView mapView;

    private ListeCarrefours listeCarrefours;

    private Carrefour selectedCarrefour1;

    private Carrefour selectedCarrefour2;

    private int numberOfSelectedCarrefours;

    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    private void initializeAllCarrefours() {
        this.listeCarrefours = new ListeCarrefours();
        this.listeCarrefours.initialisationListeCarrefours();
    }

    public void mapInitialized() {
        this.initializeAllCarrefours();

        LatLong carrefourLocation;

        Marker markerCarrefour;

        MarkerOptions markerOptionsCarrefour;

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

        GoogleMap map = mapView.createMap(mapOptions);

        for(Carrefour carrefour : this.listeCarrefours.getMesCarrefours()) {

            // Position du carrefour
            carrefourLocation = new LatLong(carrefour.getCoordY(), carrefour.getCoordX());

            // Ajout du marker sur la carte
            markerOptionsCarrefour = new MarkerOptions();
            markerOptionsCarrefour.position(carrefourLocation);

            markerCarrefour = new Marker(markerOptionsCarrefour);

            map.addMarker(markerCarrefour);

            map.addUIEventHandler(markerCarrefour, UIEventType.click, (JSObject obj) -> {

                if(numberOfSelectedCarrefours == 0) {
                    selectedCarrefour1 = carrefour;
                }

                if(numberOfSelectedCarrefours == 1) {
                    selectedCarrefour2 = carrefour;
                }

                numberOfSelectedCarrefours++;

                System.out.println(selectedCarrefour1);
                System.out.println(selectedCarrefour2);
            });
        }
    }

}
