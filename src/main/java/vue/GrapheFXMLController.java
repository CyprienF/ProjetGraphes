package vue;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import modele.Algorithmes;
import modele.Carrefour;
import modele.ListeCarrefours;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GrapheFXMLController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    public GrapheFXMLController() {
        this.initializeAllCarrefours();
    }

    @FXML
    protected GoogleMapView mapView;

    @FXML
    protected Button resetButton;

    @FXML
    protected Label selectedCarrefour1Label;

    @FXML
    protected Label selectedCarrefour2Label;

    @FXML
    protected Rectangle rectangle;

    @FXML
    protected Button launchAlgorithme;

    @FXML
    protected ChoiceBox choiceAlgorithmes;

    private GoogleMap map;

    private ListeCarrefours listeCarrefours;

    private Carrefour selectedCarrefour1;

    private Carrefour selectedCarrefour2;

    private int numberOfSelectedCarrefours;

    private LatLong carrefourLocation;

    private Marker markerCarrefour;

    private MarkerOptions markerOptionsCarrefour;

    private Algorithmes algorithmes = new Algorithmes();

    @FXML
    private void clearSelectedCarrefours(ActionEvent event) {
        this.resetButton.setVisible(false);
        this.launchAlgorithme.setVisible(false);
        this.rectangle.setVisible(false);
        this.numberOfSelectedCarrefours = 0;
        this.choiceAlgorithmes.setVisible(false);

        if(this.selectedCarrefour1 != null) {
            this.selectedCarrefour1 = null;
            this.selectedCarrefour1Label.setText("");
        }

        if(this.selectedCarrefour2 != null) {
            this.selectedCarrefour2 = null;
            this.selectedCarrefour2Label.setText("");
        }

        this.deleteAllMarkers();
        this.initializeAllMarkers(this.listeCarrefours);
    }

    @FXML
    private void launchAlgorithme(ActionEvent event) {
        if(this.selectedCarrefour1 != null && this.selectedCarrefour2 != null) {
            algorithmes.cheminLePlusCourt(listeCarrefours, this.selectedCarrefour1, this.selectedCarrefour2, this.choiceAlgorithmes.getValue().toString());
            this.deleteAllMarkers();
            this.selectedCarrefour2.addCarrefourPluscourtCHhemin(selectedCarrefour2);
            this.initializeAlgorithmeMarkers(this.selectedCarrefour2.getPlusCourtChemin());
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.selectedCarrefour1Label.setText("");
        this.selectedCarrefour2Label.setText("");
        this.resetButton.setText("Reset");
        this.launchAlgorithme.setText("Lancer l'algorithme");
        this.resetButton.setVisible(false);
        this.rectangle.setVisible(false);
        this.launchAlgorithme.setVisible(false);

        this.choiceAlgorithmes.getItems().addAll(FXCollections.observableArrayList("Dijkstra", "A*"));
        this.choiceAlgorithmes.getSelectionModel().selectFirst();
        this.choiceAlgorithmes.setVisible(false);
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
                    this.rectangle.setVisible(true);
                    this.selectedCarrefour1 = carrefour;
                    this.selectedCarrefour1Label.setText("Carrefour 1 : " + carrefour.getLibellecarrefour());

                } else if(this.numberOfSelectedCarrefours == 1) {
                    this.selectedCarrefour2 = carrefour;
                    this.selectedCarrefour2Label.setText("Carrefour 2 : " + carrefour.getLibellecarrefour());

                    this.resetButton.setVisible(true);
                    this.launchAlgorithme.setVisible(true);
                    this.choiceAlgorithmes.setVisible(true);
                }

                numberOfSelectedCarrefours++;
            });
        }
    }

    private void initializeAlgorithmeMarkers(List <Carrefour> carrefours) {
        this.deleteAllMarkers();
        for(Carrefour carrefour : carrefours) {
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
