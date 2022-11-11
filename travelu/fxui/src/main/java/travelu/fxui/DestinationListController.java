package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class DestinationListController {

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea destinationText;

    @FXML
    private Text feedbackText;

    private DestinationList destinationList;

    private String currentDestination;

    private TraveluHandler traveluHandler = new TraveluHandler();

    private String destinationListFile;
    private String currentDestinationFile;

    /**
     * Initiliaze start-page
     * 
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {

        destinationListFile = "DestinationList.json";
        currentDestinationFile = "CurrentDestinationName.json";

        // get DestinationList from file
        this.destinationList = traveluHandler.readDestinationListJSON();

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

        // Write current destination name to file, so it can be accessed from
        // destination controller
        traveluHandler.writeJSON(destinationName, currentDestinationFile);

        App.setRoot("destination");

    }

    /**
     * Add destination to list
     * 
     * @throws IOException              if error writing to file
     * @throws IllegalArgumentException if destinationName is null
     */
    @FXML
    public void handleAddDestination() {
        String newDestinationName = destinationText.getText();
        try {
            if (newDestinationName.isBlank()) {
                resetAddDestination(newDestinationName);
            } else if (destinationList.containsDestination(newDestinationName)) {
                feedbackText.setText("You have already registered this destination");
            } else {
                newDestinationWithInput(newDestinationName);
            }
            traveluHandler.writeJSON(destinationList, destinationListFile);

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Subfunction for handleAddDestination where it resets the textbox for
     * addDestination
     * 
     * @param textField
     */
    private void resetAddDestination(String textField) {
        if (textField.isBlank()) {
            feedbackText.setText("");
        }
    }

    /**
     * Subfunction for handleAddDestination where it creates a new Destination
     * object with given name
     * 
     * @param destinationName
     */
    private void newDestinationWithInput(String destinationName) {
        // if everything is ok with the input
        // create new destination with input as name
        Destination newDestination = new Destination(destinationName.strip(), null, 0, new ArrayList<String>(), null);
        listView.getItems().add(newDestination.getName());
        destinationList.addDestination(newDestination);

        // remove any feedback given
        feedbackText.setText("");

        // remove text in inputField
        destinationText.clear();

    }

    /**
     * Removes destination from list
     * 
     * @throws IOException if error writing to file
     */
    @FXML
    public void handleRemoveDestination() {
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
        try {
            traveluHandler.writeJSON(destinationList, destinationListFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // For testing purposes
    public List<String> getDestinationListNames() {
        return destinationList.getDestinationNames();
    }

    public void initiliazeFromTestFiles() throws IOException {
        destinationListFile = "testDestinationList.json";
        currentDestinationFile = "testCurrentDestinationName.json";

        destinationList = traveluHandler.readDestinationListJSON(destinationListFile);

        setUpListView();
    }

}
