package travelu.fxui;

import java.io.FileNotFoundException;
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
import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.scene.shape.SVGPath;

public class DestinationController {

    /**
     * Initialize client used for server communication
     */
    private final Client client = new Client("http://localhost", 8080);

    // currently selected destination
    private Destination currentDestination;
    private DestinationList destinationList;
    private TraveluHandler traveluHandler = new TraveluHandler();

    // currently selected activity
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

    @FXML
    private void initialize() {

        try {
            this.currentDestination = this.client.getDestination();
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            // TODO: handle exception
        }

        colorStars(this.currentDestination.getRating());

        destinationLabel.setText(currentDestination.getName());

        if (this.currentDestination.getComment() != null) {
            commentTextField.setText(this.currentDestination.getComment());
        }

        arrivalDateLabel.setText(currentDestination.getDateInterval().getArrivalDate());
        departureDateLabel.setText(currentDestination.getDateInterval().getDepartureDate());

        // Standardizes date formatting in datePicker. Largely copied from documentation
        // for datePicker.setconverter
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
             * Returns null if date is invalid
             */
            @Override
            public String toString(LocalDate date) {
                try {
                    if (date != null) {
                        return formatter.format(date);
                    } else {
                        return "";
                    }
                } catch (DateTimeException dte) {
                    return "";
                }
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
                    } else {
                        return null;
                    }
                } catch (DateTimeParseException dtpe) {
                    return null;
                }

            }
        };

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
        // make currentDestination the selected list-view item
        activitiesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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
        activitiesListView.getItems().addAll(this.currentDestination.getActivities());
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
     * 
     * 
     * @throws IOException              in case of filehandling issue
     * @throws IllegalArgumentException if input is blank or already existing
     */
    @FXML
    private void handleAddActivity() {
        String activity = newActivityTextField.getText();

        try {
            currentDestination.addActivity(activity);
            this.client.addActivity(activity);
            activityFeedbackLabel.setText("");
        } catch (IllegalArgumentException iae) {
            activityFeedbackLabel.setText("Add unique activity to update.");
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            // TODO: give relevant user feedback
        }

        updateListView();
        newActivityTextField.setText("");
    }

    /**
     * Removes activity from list of activities, and updates activitiesListView
     */
    @FXML
    private void handleRemoveActivity() {
        if (currentActivity != null) {
            try {
                this.client.removeActivity(currentActivity);
            } catch (URISyntaxException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            currentDestination.removeActivity(currentActivity);
            updateListView();
        }
    }

    /**
     * call method handleStar with parameter based on which star was clicked
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

        currentDestination.setRating(starNumber);

        colorStars(starNumber);

        try {
            this.client.setRating(starNumber);
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Color starNumber stars yellow, and the rest of the stars white
     * 
     * @param rating
     */
    private void colorStars(int starNumber) {

        // Sets color of the clicked star, and all stars before it to yellow. Updates
        // color of all stars after clicked star to white.
        if (starNumber >= 1) {
            star1.setStyle("-fx-fill: #FFD700");
        } else {
            star1.setStyle("-fx-fill: #FFFFFF");
        }

        if (starNumber >= 2) {
            star2.setStyle("-fx-fill: #FFD700");
        } else {
            star2.setStyle("-fx-fill: #FFFFFF");
        }

        if (starNumber >= 3) {
            star3.setStyle("-fx-fill: #FFD700");
        } else {
            star3.setStyle("-fx-fill: #FFFFFF");
        }

        if (starNumber >= 4) {
            star4.setStyle("-fx-fill: #FFD700");
        } else {
            star4.setStyle("-fx-fill: #FFFFFF");
        }

        if (starNumber >= 5) {
            star5.setStyle("-fx-fill: #FFD700");
        } else {
            star5.setStyle("-fx-fill: #FFFFFF");
        }
    }

    /**
     * Changes comment, and writes this to file
     *
     */
    @FXML
    private void handleChangeComment() {
        String newComment = commentTextField.getText();
        currentDestination.setComment(newComment);
        try {
            this.client.updateComment(newComment);
            commentFeedbackLabel.setText("Comment updated!");

        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
            currentDestination.setArrivalDate(arrivalDate);
            arrivalDateLabel.setText(currentDestination.getDateInterval().getArrivalDate());
            this.client.setArrivalDate(arrivalDate);
            dateUpdatedFeedbackLabel.setText("");
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            arrivalDatePicker.getEditor().setText("");
            dateUpdatedFeedbackLabel.setText(e.getMessage());
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
            currentDestination.setDepartureDate(departureDate);
            departureDateLabel.setText(currentDestination.getDateInterval().getDepartureDate());
            this.client.setDepartureDate(departureDate);
            dateUpdatedFeedbackLabel.setText("");
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            departureDatePicker.getEditor().setText("");
            dateUpdatedFeedbackLabel.setText(e.getMessage());
        }

    }

    // For testing purposes
    public String getDestination() {
        return currentDestination.getName();
    }

    public List<String> getDestinationActivities() {
        return currentDestination.getActivities();
    }

    public String getDestinationComment() {
        return currentDestination.getComment();
    }

    public DateInterval getDestinationDateInterval() {
        return currentDestination.getDateInterval();
    }

    public int getDestinationRating() {
        return currentDestination.getRating();
    }

    public void initializeFromTestFiles() throws FileNotFoundException, IOException {

        // destinationListFile = "testDestinationList.json";

        this.destinationList = traveluHandler.readDestinationListJSON("testDestinationList.json");
        String currentDestinationName = traveluHandler
                .readCurrentDestinationNameJSON("testCurrentDestinationName.json");

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

        destinationLabel.setText(currentDestinationName);

        colorStars(this.currentDestination.getRating());

        commentTextField.setText("");
        if (this.currentDestination.getComment() != null) {
            commentTextField.setText(this.currentDestination.getComment());
        }

        updateListView();
    }

}
