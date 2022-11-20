package travelu.fxui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import travelu.client.Client;
import travelu.client.ServerException;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
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
    private Label feedbackLabel;

    // List of destinations
    private DestinationList destinationList;

    // Name of the currently selected destination
    private String currentDestination;

    /**
     * Initialize start-page.
     * 
     * @throws IOException
     */
    @FXML
    private void initialize() {

        try {
            // Get destination list from server
            this.destinationList = client.getDestinationList();
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            // TODO better handling
        }

        setUpListView();

    }

    /**
     * Make the list view be a list of all destination names in the desitnation
     * list.
     * 
     * Each name with a number of stars at the end equal to the rating.
     */
    private void setUpListView() {

        // Set font size in list view to 20px
        listView.setStyle("-fx-font-size:20;");

        // Clear list view
        listView.getItems().clear();

        // Create list of all destinations with star-rating
        List<String> destinationNameAndRating = new ArrayList<>();
        // TODO: Stop interacting directly with core through destinationList
        for (String destinationName : destinationList.getDestinationNames()) {
            try {
                // Get rating of destination
                int destinationRating = destinationList.getDestinationCopyByName(destinationName).getRating();
                // Add destination with name and number stars equal to rating
                destinationNameAndRating.add(destinationName + "★".repeat(destinationRating));
            } catch (NoSuchElementException nsee) {
                feedbackLabel.setText("No such element: " + nsee.getMessage());
            }

        }

        // Add all destinations and rating to list-view
        listView.getItems().addAll(destinationNameAndRating);

        // Make click select currentDestination
        // Make double-click on list-view item take you to page with currentDestination
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getTarget().toString().contains("'")) {
                    // If you click on the box around the text the format is
                    // objectinformation'DestinationName'
                    // We then need to get the element after the first '
                    currentDestination = click.getTarget().toString()
                            .split("'")[1];
                } else if (click.getTarget().toString().contains("\"")) {
                    // If you click directly on the text the format is
                    // Text[text="DestinationName" objectinformation="..."]]
                    // We then need to get the element after the first "
                    currentDestination = click.getTarget().toString()
                            .split("\"")[1];
                }

                // We want currentDestination to be null instead of not "null"
                if (currentDestination != null && currentDestination.equals("null")) {
                    currentDestination = null;
                }

                // Switch to desitnation page on double-click
                if (click.getClickCount() == 2) {
                    // If currentDestination is null, the user has not clicked a destination
                    if (currentDestination != null) {
                        // Remove the stars from the selected destination
                        String currentDestinationName = currentDestination.replace("★", "");
                        try {
                            // Load the destination chosen
                            switchToDestination(currentDestinationName);
                        } catch (IOException e) {
                            // Give feedback to user that the destination could not be found
                            feedbackLabel.setText("Could not find " + currentDestinationName);
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
            client.storeCurrentDestinationName(destinationName);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            // TODO better handling
        }

        App.setRoot("destination");

    }

    /**
     * Add destination to list
     * 
     * @throws IllegalArgumentException if destinationName is null
     */

    public void handleAddDestination() {
        String newDestinationName = destinationText.getText().trim();
        try {
            if (newDestinationName.isBlank()) {
                // If user didn't input any text
                // Remove any feedback given and do nothing
                feedbackLabel.setText("");
            }
            // TODO: Stop interacting directly with core through destinationList
            else if (destinationList.containsDestination(newDestinationName)) {
                // If the input text matches any of the already registrations
                // Give feedback
                feedbackLabel.setText("You have already registered this destination");
            } else if (!newDestinationName.matches("[A-Za-z\\s\\-]+")) {
                // If the input text contains anything but letters, spaces and dashes
                feedbackLabel.setText("Destination name must contain only letters, spaces and dashes");
            } else {
                // If everything is ok with the input
                // Create new destination with input as name
                Destination newDestination = new Destination(newDestinationName.strip(), null, 0,
                        new ArrayList<String>(), null);

                // Add destination to list-view and destinations
                listView.getItems().add(newDestination.getName());

                // TODO: Stop interacting directly with core through destinationList
                destinationList.addDestination(newDestination);

                // Remove any feedback given
                feedbackLabel.setText("");

                // Remove text in inputField
                destinationText.clear();

                // Add destination to server
                client.addDestination(newDestination);
            }
        } catch (IllegalArgumentException iae) {
            feedbackLabel.setText("");
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            // TODO better handling
        }

    }

    /**
     * Removes destination from list
     */
    @FXML
    public void handleRemoveDestination() {
        if (currentDestination == null) {
            // If there is no selected destination
            // Give user feedback
            feedbackLabel.setText("Please select a destination you would like to remove");
        } else {
            // If there is a selected destination
            // Remove the selected destination from destinations and list-view
            // Remove the star-rating from the selected destination
            String currentDestinationName = currentDestination.replace("★", "");

            try {
                // Clear feedback if everything went well
                feedbackLabel.setText("");
                client.removeDestination(currentDestinationName);

                // Remove the destination from destinationList and list-view
                // TODO: Stop interacting directly with core through destinationList
                destinationList.removeDestination(currentDestinationName);
                listView.getItems().remove(currentDestination);
                currentDestination = null;

            } catch (NoSuchElementException nsee) {
                feedbackLabel.setText("Please select a destination you would like to remove");
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            } catch (ServerException se) {
                errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
            } catch (ExecutionException ee) {
                ee.printStackTrace();
                // TODO better handling
            }
        }
    }

    /**
     * Sort the list of destinations alphabetically from A to Z
     */
    @FXML
    public void handleSortByName() {

        // TODO: Stop interacting directly with core through destinationList
        destinationList.sortByName();

        setUpListView();
    }

    /**
     * Sort the list of destinations by rating from highest to lowest
     */
    @FXML
    public void handleSortByRating() {

        // TODO: Stop interacting directly with core through destinationList
        destinationList.sortByRating();

        setUpListView();
    }

    /*
     * Creates a popup with the given title and message that will be shown until
     * closed
     */
    private void errorPopup(String type, String message) {
        Alert invalidInput = new Alert(AlertType.WARNING);
        invalidInput.setTitle(type);
        invalidInput.setHeaderText(message);
        invalidInput.showAndWait();
    }

}
