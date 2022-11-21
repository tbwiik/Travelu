package travelu.fxui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import javafx.util.StringConverter;
import javafx.scene.shape.SVGPath;

public class DestinationController {

    /**
     * Client used for server communication
     */
    private final Client client = new Client("http://localhost", 8080);

    // Currently selected destination
    private Destination currentDestination;

    // Currently selected activity
    private String currentActivity;

    @FXML
    Label destinationLabel;

    @FXML
    Label arrivalDateLabel;

    @FXML
    Label departureDateLabel;

    @FXML
    DatePicker arrivalDatePicker;

    @FXML
    DatePicker departureDatePicker;

    @FXML
    Label dateUpdatedFeedbackLabel;

    @FXML
    ListView<String> activitiesListView;

    @FXML
    TextField newActivityTextField;

    @FXML
    TextField commentTextField;

    @FXML
    Label commentFeedbackLabel;

    @FXML
    Label activityFeedbackLabel;

    @FXML
    SVGPath star1;

    @FXML
    SVGPath star2;

    @FXML
    SVGPath star3;

    @FXML
    SVGPath star4;

    @FXML
    SVGPath star5;

    /**
     * Loads the current destination selected from the server and displays its
     * information
     */
    @FXML
    private void initialize() {

        try {
            currentDestination = client.getCurrentDestination();
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // TODO better handling
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

        // Standardizes date formatting in datePicker
        // Code gotten from documentation for datePicker.setconverter
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html#setConverter-javafx.util.StringConverter-
        StringConverter<LocalDate> stringConverter = new StringConverter<LocalDate>() {
            // Standard date formatting
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            {
                // Display format in DatePicker text fields
                arrivalDatePicker.setPromptText(pattern.toLowerCase());
                departureDatePicker.setPromptText(pattern.toLowerCase());
            }

            /**
             * Generates string from LocalDate object, used for displaying selected date in
             * DatePicker text field
             * <p>
             * Returns empty string if date is invalid
             */
            @Override
            public String toString(LocalDate date) {
                try {
                    if (date != null) {
                        return formatter.format(date);
                    }
                } catch (DateTimeException dte) {
                }
                return "";
            }

            /**
             * Generates LocalDate object from string, used for validating written input
             * date
             */
            @Override
            public LocalDate fromString(String string) {
                try {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, formatter);
                    }
                } catch (DateTimeParseException dtpe) {
                }
                return null;
            }
        };

        // Set the date pickers to both use the string converter
        arrivalDatePicker.setConverter(stringConverter);
        departureDatePicker.setConverter(stringConverter);

        setupListView();
        updateListView();
    }

    /**
     * Set up listener for changing selected activity in activitiesListView
     */
    @FXML
    private void setupListView() {
        // Make currentDestination get set to the selected list-view item
        activitiesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Update currentActivity field
                currentActivity = activitiesListView.getSelectionModel().selectedItemProperty().getValue();
            }
        });
    }

    /**
     * Update view of activity list
     */
    @FXML
    private void updateListView() {
        activitiesListView.getItems().clear();
        activitiesListView.getItems().addAll(currentDestination.getActivities());
    }

    /**
     * Return to destination-list
     * 
     * @throws IOException
     */
    @FXML
    private void handleReturnButton() throws IOException {
        App.setRoot("destinationList");
    }

    /**
     * Adds activity to the list of activities, and updates activitiesListView
     * Updates activityFeedbackLabel to give feedback to user if any errors occur
     */
    @FXML
    private void handleAddActivity() {
        // Get the text from the text field
        String activity = newActivityTextField.getText();

        try {
            // Add activity to the current destination
            currentDestination.addActivity(activity);
            client.addActivity(activity);

            // Clear feedback label if everything went well
            activityFeedbackLabel.setText("");
        } catch (IllegalArgumentException iae) {
            activityFeedbackLabel.setText("Add unique activity to update.");
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // TODO better handling
        }

        updateListView();

        // Clear text field when done
        newActivityTextField.setText("");
    }

    /**
     * Removes activity from list of activities, and updates activitiesListView
     */
    @FXML
    private void handleRemoveActivity() {
        if (currentActivity != null) {
            try {
                // Remove activity from the server
                client.removeActivity(currentActivity);
            } catch (ServerException se) {
                errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
            } catch (ExecutionException | URISyntaxException | InterruptedException e) {
                e.printStackTrace();
                // TODO better handling
            }

            // Remove the activity from the current destination object
            currentDestination.removeActivity(currentActivity);
            updateListView();
        }
    }

    /**
     * Call method handleStar with parameter based on which star was clicked
     */
    @FXML
    private void handleStar1() {
        handleStar(1);
    }

    @FXML
    private void handleStar2() {
        handleStar(2);
    }

    @FXML
    private void handleStar3() {
        handleStar(3);
    }

    @FXML
    private void handleStar4() {
        handleStar(4);
    }

    @FXML
    private void handleStar5() {
        handleStar(5);
    }

    /**
     * Set rating of current destination to starNumber, and update stars
     * 
     * @param starNumber
     */
    private void handleStar(int starNumber) {

        // Set the rating to the star number clicked
        currentDestination.setRating(starNumber);

        colorStars(starNumber);

        try {
            client.setRating(starNumber);
        } catch (ServerException se) {
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // TODO better handling
        }
    }

    /**
     * Color starNumber stars from the left yellow, and the rest of the stars white
     * 
     * @param rating
     */
    private void colorStars(int starNumber) {
        List<SVGPath> stars = new ArrayList<>(List.of(star1, star2, star3, star4, star5));
        // Color first starNumber stars yellow, the rest white
        for (int i = 0; i < stars.size(); i++) {
            if (i < starNumber)
                stars.get(i).setStyle("-fx-fill: #FFD700");
            else
                stars.get(i).setStyle("-fx-fill: #FFFFFF");
        }
    }

    /**
     * Updates comment and displays feedback to user if successful
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
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());

            // Clear comment in case it shows "Comment updated!" when it wasn't
            commentFeedbackLabel.setText("");
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // TODO better handling
        }
    }

    /**
     * Sets arrival date, and catches exceptions due to date validation errors
     */
    @FXML
    private void handleSetArrivalDate() {
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
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // TODO better handling
        }

    }

    /**
     * Sets departure date, and catches exceptions due to date validation errors
     */
    @FXML
    private void handleSetDepartureDate() {

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
            errorPopup("Error", se.getMessage() + " with status: " + se.getStatusCode());
        } catch (ExecutionException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // TODO better handling
        }

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
