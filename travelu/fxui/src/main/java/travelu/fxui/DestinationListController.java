package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

        listView.getItems().clear();

        // create list of all destinations with star-rating
        List<String> destinationNameAndRating = new ArrayList<>();
        for (String destinationName : destinationList.getDestinationNames()) {
            // get rating of destination
            int destinationRating = destinationList.getDestinationCopyByName(destinationName).getRating();
            // add destination with name and number stars equal to rating
            destinationNameAndRating.add(destinationName + " " + "★".repeat(destinationRating));
        }

        // add all destinations and rating to list-view
        listView.getItems().addAll(destinationNameAndRating);

        // make click select currentDestination
        // make double-click on list-view item take you to page with currentDestination
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                // set currentDestination to the selected item from input on format
                // objectinformation'DestinationName'
                currentDestination = click.getTarget().toString().split("'")[1];

                if (click.getClickCount() == 2) {

                    // switch to currentDestination page on double-click if a destination was
                    // clicked
                    if (!currentDestination.equals("null")) {
                        // remove the stars from the selected destination
                        String currentDestinationName = currentDestination.replace("★", "");
                        try {
                            // load the destination chosen
                            switchToDestination(currentDestinationName);
                        } catch (IOException e) {
                            feedbackText.setText("Could not find " + currentDestinationName);
                            e.printStackTrace();
                        }
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
        } else if (!newDestinationName.matches("[A-Za-z\\s\\-]+")) {
            // if the input text contains anything but letters, spaces and dashes
            feedbackText.setText("Destination name must contain only letters, spaces and dashes");
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
        }
        traveluHandler.writeJSON(destinationList, destinationListFile);

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
            // remove the star-rating from the selected destination
            String currentDestinationName = currentDestination.replace("★", "");

            // remove the destination from destinationList and list-view
            destinationList.removeDestination(currentDestinationName);
            listView.getItems().remove(currentDestination);
        }
        traveluHandler.writeJSON(destinationList, destinationListFile);
    }

    @FXML
    public void handleSortByName() {

        destinationList.sortByName();

        setUpListView();
    }

    @FXML
    public void handleSortByRating() {

        destinationList.sortByRating();

        setUpListView();
    }

    // For testing purposes
    public List<String> getDestinationListNames() {
        return destinationList.getDestinationNames();
    }

    public List<String> getListViewItems() {
        return new ArrayList<String>(listView.getItems());
    }

    public void initiliazeFromTestFiles() throws IOException {
        destinationListFile = "testDestinationList.json";
        currentDestinationFile = "testCurrentDestinationName.json";

        destinationList = traveluHandler.readDestinationListJSON(destinationListFile);

        setUpListView();
    }

}
