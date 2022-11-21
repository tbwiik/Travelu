package travelu.fxui;

import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import travelu.client.Client;
import travelu.client.ServerException;

import travelu.core.Destination;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.SVGPath;

public class DestinationController {

    /**
     * Client used for server communication.
     */
    private final Client client = new Client("http://localhost", 8080);

    /**
     * Displayed and selected destination.
     */
    private Destination currentDestination;

    /**
     * Currently selected activity.
     */
    private String currentActivity;

    /**
     * Label displaying destination name.
     */
    @FXML
    private Label destinationLabel;

    /**
     * Label displaying arrival date, in format dd/MM/yyyy.
     */
    @FXML
    private Label arrivalDateLabel;

    /**
     * Label displaying departure date, in format dd/MM/yyyy.
     */
    @FXML
    private Label departureDateLabel;

    /**
     * DatePicker-element for selecting arrival date.
     */
    @FXML
    private DatePicker arrivalDatePicker;

    /**
     * DatePicker-element for selecting departure date.
     */
    @FXML
    private DatePicker departureDatePicker;

    /**
     * Label for giving feedback related to dates.
     */
    @FXML
    private Label dateUpdatedFeedbackLabel;

    /**
     * ListView containing acitivities as Strings.
     */
    @FXML
    private ListView<String> activitiesListView;

    /**
     * TextField for typing in new activity.
     */
    @FXML
    private TextField newActivityTextField;

    /**
     * Textfield for typing in new comment.
     */
    @FXML
    private TextField commentTextField;

    /**
     * Label for giving feedback related to comment.
     */
    @FXML
    private Label commentFeedbackLabel;

    /**
     * Label for giving feedback related to activities.
     */
    @FXML
    private Label activityFeedbackLabel;

    /**
     * First star element.
     */
    @FXML
    private SVGPath star1;

    /**
     * Second star element.
     */
    @FXML
    private SVGPath star2;

    /**
     * Third star element.
     */
    @FXML
    private SVGPath star3;

    /**
     * Fourth star element.
     */
    @FXML
    private SVGPath star4;

    /**
     * Fifth star element.
     */
    @FXML
    private SVGPath star5;

    /**
     * Loads the current destination selected from the server and displays its
     * information.
     */
    @FXML
    private void initialize() {

        // Load destination
        try {
            currentDestination = client.getCurrentDestination();
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

        // Set the destination label to the name of the destination
        destinationLabel.setText(currentDestination.getName());

        // Color the stars according to the rating
        colorStars(currentDestination.getRating());

        // Set comment text field to the comment of the destination if it exists
        String comment = currentDestination.getComment() == null ? "" : currentDestination.getComment();
        commentTextField.setText(comment);

        // Set the arrival and departure labels to the arrival and departure dates
        arrivalDateLabel.setText(currentDestination.getDateInterval().getArrivalDate());
        departureDateLabel.setText(currentDestination.getDateInterval().getDepartureDate());

        // Set prompt text to display correct format
        String pattern = "dd/MM/yyyy";
        arrivalDatePicker.setPromptText(pattern.toLowerCase());
        departureDatePicker.setPromptText(pattern.toLowerCase());

        // Init string converter
        DateConverter dateConverter = new DateConverter();

        // Set the date pickers to both use string converter
        arrivalDatePicker.setConverter(dateConverter);
        departureDatePicker.setConverter(dateConverter);

        setupListView();
        updateListView();
    }

    /**
     * Set up listener for changing selected activity in activitiesListView.
     */
    @FXML
    private void setupListView() {
        // Make currentDestination get set to the selected list-view item
        activitiesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                // Update currentActivity field
                currentActivity = activitiesListView.getSelectionModel().selectedItemProperty().getValue();
            }
        });
    }

    /**
     * Update view of activity list.
     */
    @FXML
    private void updateListView() {
        activitiesListView.getItems().clear();
        activitiesListView.getItems().addAll(currentDestination.getActivities());
    }

    /**
     * Return to destination-list.
     */
    @FXML
    private void handleReturnButton() {
        try {
            App.setRoot("destinationList");
        } catch (Exception e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
        }
    }

    /**
     * Adds activity to the list of activities, and updates activitiesListView.
     * Updates activityFeedbackLabel to give feedback to user if any errors occur
     */
    @FXML
    private void handleAddActivity() {
        // Get the text from the text field
        String activity = newActivityTextField.getText();

        // Add and save activity
        try {
            // Add activity to the current destination
            currentDestination.addActivity(activity);
            client.addActivity(activity);

            // Clear feedback label if everything went well
            activityFeedbackLabel.setText("");
        } catch (IllegalArgumentException iae) {
            activityFeedbackLabel.setText("Add unique activity to update.");
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

        updateListView();

        // Clear text field when done
        newActivityTextField.setText("");
    }

    /**
     * Removes activity from list of activities, and updates activitiesListView.
     */
    @FXML
    private void handleRemoveActivity() {
        if (currentActivity != null) {

            // Save remove activity through server
            try {
                client.removeActivity(currentActivity);
            } catch (ServerException se) {
                errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
            } catch (ExecutionException | URISyntaxException | InterruptedException e) {
                errorPopup("Error", "Error: \n" + e.getMessage());
                e.printStackTrace();
            }

            // Remove the activity from the current destination object
            currentDestination.removeActivity(currentActivity);
            updateListView();
        }
    }

    /**
     * Call method handleStar with parameter based on which star was clicked.
     */
    @FXML
    private void handleStar1() {
        final int starNumber = 1;
        handleStar(starNumber);
    }

    @FXML
    private void handleStar2() {
        final int starNumber = 2;
        handleStar(starNumber);
    }

    @FXML
    private void handleStar3() {
        final int starNumber = 3;
        handleStar(starNumber);
    }

    @FXML
    private void handleStar4() {
        final int starNumber = 4;
        handleStar(starNumber);
    }

    @FXML
    private void handleStar5() {
        final int starNumber = 5;
        handleStar(starNumber);
    }

    /**
     * Set rating of current destination to starNumber, and update stars.
     *
     * @param starNumber
     */
    private void handleStar(final int starNumber) {

        // Set the rating to the star number clicked
        currentDestination.setRating(starNumber);

        colorStars(starNumber);

        // Save rating
        try {
            client.setRating(starNumber);
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Color starNumber stars from the left yellow, and the rest of the stars white.
     *
     * @param starNumber the number of stars to color, i.e. the rating given
     */
    private void colorStars(final int starNumber) {
        // List with stars
        List<SVGPath> stars = new ArrayList<>(List.of(star1, star2, star3, star4, star5));

        // Color first starNumber stars yellow, the rest white
        for (int i = 0; i < stars.size(); i++) {
            if (i < starNumber) {
                stars.get(i).setStyle("-fx-fill: #FFD700");
            } else {
                stars.get(i).setStyle("-fx-fill: #FFFFFF");
            }
        }
    }

    /**
     * Updates comment and displays feedback to user if successful.
     * Shows error popup if unsuccessful
     */
    @FXML
    private void handleChangeComment() {

        String newComment = commentTextField.getText();
        currentDestination.setComment(newComment);

        try {

            client.updateComment(newComment);
            commentFeedbackLabel.setText("Comment updated!");

        } catch (ServerException se) {

            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());

            // Clear comment in case it shows "Comment updated!" when it wasn't
            commentFeedbackLabel.setText("");

        } catch (ExecutionException | URISyntaxException | InterruptedException e) {

            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * Sets arrival date, and catches exceptions due to date validation errors.
     */
    @FXML
    private void handleSetArrivalDate() {

        // Get date and format correctly
        String arrivalDate = arrivalDatePicker.getValue() == null ? ""
                : arrivalDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            // This throws an exception if the date is invalid and therefore gets caught
            currentDestination.setArrivalDate(arrivalDate);
            client.setArrivalDate(arrivalDate);

            // Updates arrival date label
            arrivalDateLabel.setText(currentDestination.getDateInterval().getArrivalDate());

            // Clear feedback label if everything went well
            dateUpdatedFeedbackLabel.setText("");

        } catch (IllegalArgumentException | IllegalStateException e) {
            arrivalDatePicker.getEditor().setText("");
            dateUpdatedFeedbackLabel.setText(e.getMessage());
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Sets departure date, and catches exceptions due to date validation errors.
     */
    @FXML
    private void handleSetDepartureDate() {

        // Get date and format correctly
        String departureDate = departureDatePicker.getValue() == null ? ""
                : departureDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            // This throws an exception if the date is invalid and therefore gets catched
            currentDestination.setDepartureDate(departureDate);
            client.setDepartureDate(departureDate);

            // Updates arrival date label
            departureDateLabel.setText(currentDestination.getDateInterval().getDepartureDate());

            // Clear feedback label if everything went well
            dateUpdatedFeedbackLabel.setText("");
        } catch (IllegalArgumentException | IllegalStateException e) {
            departureDatePicker.getEditor().setText("");
            dateUpdatedFeedbackLabel.setText(e.getMessage());
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + "\n Status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            errorPopup("Error", "Error: \n" + e.getMessage());
            e.printStackTrace();
        }

    }

    /*
     * Creates a popup with the given title and message that will be shown until
     * closed.
     */
    private void errorPopup(final String type, final String message) {
        Alert invalidInput = new Alert(AlertType.WARNING);
        invalidInput.setTitle(type);
        invalidInput.setHeaderText(message);
        invalidInput.showAndWait();
    }

}
