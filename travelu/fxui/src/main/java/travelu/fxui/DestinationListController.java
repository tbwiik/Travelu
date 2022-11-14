package travelu.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import travelu.client.Client;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class DestinationListController {

    /**
     * Initialize client for server communication
     */
    private final Client client = new Client("http://localhost", 8080);

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea destinationText;

    @FXML
    private Text feedbackText;

    private DestinationList destinationList;

    private String currentDestination;

    private String destinationListFile;

    /**
     * Initialize start-page
     * 
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {

        try {
            this.destinationList = client.getDestinationList();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO better handling
        }

        setUpListView();
    }

    private void setUpListView() {

        listView.setStyle("-fx-font-size:20;");

        // add all destinations to the list-view
        listView.getItems()
                .addAll(destinationList.getDestinationNames());

        // make currentDestination the selected list-view item
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentDestination = listView.getSelectionModel().selectedItemProperty().getValue();
            }
        });

        // make double-click on list-view item take you to page with currentDestination
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    try {
                        switchToDestination(currentDestination);
                    } catch (IOException e) {
                        feedbackText.setText("Could not find " + currentDestination);
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Switch to Destination-View
     * 
     * @param destinationName
     * @throws IOException if error initializing new page
     */
    private void switchToDestination(String destinationName) throws IOException {

        try {
            client.storeCurrentDestination(destinationName);
        } catch (Exception e) {
            // TODO: handle exception
        }

        App.setRoot("destination");

    }

    /**
     * Add destination to list
     * 
     * @throws IOException if error writing to file
     */
    @FXML
    public void handleAddDestination() throws IOException {

        String newDestinationName = destinationText.getText().trim();
        if (newDestinationName.isBlank()) {
            // if user didn't input any text
            // remove any feedback given and do nothing
            feedbackText.setText("");
        } else if (destinationList.containsDestination(newDestinationName)) {
            // if the input text matches any of the already registrations
            // give feedback
            feedbackText.setText("You have already registered this destination");
        } else {
            // if everything is ok with the input
            // create new destination with input as name
            Destination newDestination = new Destination(newDestinationName.strip(), null, 0, new ArrayList<String>(),
                    null);

            // add destination to list-view and destinations
            listView.getItems().add(newDestination.getName());
            destinationList.addDestination(newDestination);

            // remove any feedback given
            feedbackText.setText("");

            // remove text in inputField
            destinationText.clear();

            try {
                client.addDestination(newDestination);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    /**
     * Removes destination from list
     * 
     * @throws IOException if error writing to file
     */
    @FXML
    public void handleRemoveDestination() throws IOException {
        if (currentDestination == null) {
            // if there is no selected destination
            // give user feedback
            feedbackText.setText("Please select a destination you would like to remove");
        } else {
            // if there is a selected destination
            // remove the selected destination from destinations and list-view
            destinationList.removeDestination(currentDestination);
            listView.getItems().remove(currentDestination);
        }
        // traveluHandler.writeJSON(destinationList, destinationListFile);

        // try {
        // client.saveDestinationList(destinationList);
        // } catch (Exception e) {
        // // TODO: handle exception
        // }
    }

    // For testing purposes
    public List<String> getDestinationListNames() {
        return destinationList.getDestinationNames();
    }

    public void initiliazeFromTestFiles() throws IOException {
        destinationListFile = "testDestinationList.json";

        destinationList = TraveluHandler.readDestinationListJSON(destinationListFile);

        setUpListView();
    }

}
