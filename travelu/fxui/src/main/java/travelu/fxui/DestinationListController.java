package travelu.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

        listView.getItems().clear();

        // create list of all destinations with star-rating
        List<String> destinationNameAndRating = new ArrayList<>();
        for (String destinationName : destinationList.getDestinationNames()) {
            // get rating of destination
            int destinationRating = destinationList.getDestinationCopyByName(destinationName).getRating();
            // add destination with name and number stars equal to rating
            destinationNameAndRating.add(destinationName + "★".repeat(destinationRating));
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

                if (click.getTarget().toString().contains("'")) {
                    // if you click on the box around the text the format is
                    // objectinformation'DestinationName'
                    // we then need to get the element after the first '
                    currentDestination = click.getTarget().toString()
                            .split("'")[1];
                } else {
                    // if you click directly on the text the format is
                    // Text[text="DestinationName" objectinformation="..."]]
                    // we then need to get the element after the first "
                    currentDestination = click.getTarget().toString()
                            .split("\"")[1];
                }

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

            try {
                client.removeDestination(currentDestination);
            } catch (Exception e) {
                // TODO: handle exception
            }

            // if there is a selected destination
            // remove the selected destination from destinations and list-view
            // remove the star-rating from the selected destination
            String currentDestinationName = currentDestination.replace("★", "");

            // remove the destination from destinationList and list-view
            destinationList.removeDestination(currentDestinationName);
            listView.getItems().remove(currentDestination);
        }

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

        destinationList = TraveluHandler.readDestinationListJSON(destinationListFile);

        setUpListView();
    }

}
