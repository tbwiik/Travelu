package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;
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

    // currently selected destination
    private Destination currentDestination;
    private DestinationList destinationList;
    private TraveluHandler traveluHandler = new TraveluHandler();

    // currently selected activity
    private String currentActivity;

    private String destinationListFile;

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
    private void initialize() throws FileNotFoundException, IOException {

        destinationListFile = "DestinationList.json";

        this.destinationList = traveluHandler.readDestinationListJSON();
        String currentDestinationName = traveluHandler.readCurrentDestinationNameJSON();

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

        colorStars(this.currentDestination.getRating());

        destinationLabel.setText(currentDestinationName);

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
             */
            @Override
            public String toString(LocalDate date) {
                try {
                    if (date != null) {
                        return formatter.format(date);
                    } else {
                        return "";
                    }
                } catch (Exception e) {
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
                } catch (Exception e) {
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
     * Sets up listener for changing selected activity in activitiesListView
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
     * Updates view of activity list
     */
    @FXML
    private void updateListView() {
        activitiesListView.getItems().clear();
        activitiesListView.getItems().addAll(this.currentDestination.getActivities());
    }

    /**
     * Returns to destination-list
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
            writeChanges();
            activityFeedbackLabel.setText("");
        } catch (IllegalArgumentException iae) {
            activityFeedbackLabel.setText("Add unique activity to update.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
                currentDestination.removeActivity(currentActivity);
                updateListView();
                writeChanges();
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Updates changes to currentDestination, and writes these to json.
     * 
     * @throws IOException in case of filehandling issue
     */
    private void writeChanges() throws IOException {
        this.destinationList.updateDestination(currentDestination);

        traveluHandler.writeJSON(this.destinationList, destinationListFile);
    }

    @FXML
    private void handleSelectFile() {
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
            writeChanges();
        } catch (IOException e) {
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
     * @throws IOException in case of filehandling issue
     */
    @FXML
    private void handleChangeComment() {
        String newComment = commentTextField.getText();
        currentDestination.setComment(newComment);
        try {
            writeChanges();
            if (!newComment.isBlank()) {
                commentFeedbackLabel.setText("Comment updated!");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
            writeChanges();
            dateUpdatedFeedbackLabel.setText("");
        } catch (Exception e) {
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
            writeChanges();
            dateUpdatedFeedbackLabel.setText("");
        } catch (Exception e) {
            departureDatePicker.getEditor().setText("");
            dateUpdatedFeedbackLabel.setText(e.getMessage());
        }

    }

    public void changeFileWritingName(String fileWritingName) {
        this.destinationListFile = fileWritingName;
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

        destinationListFile = "testDestinationList.json";

        this.destinationList = traveluHandler.readDestinationListJSON("testDestinationList.json");
        String currentDestinationName = traveluHandler
                .readCurrentDestinationNameJSON("testCurrentDestinationName.json");

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

        destinationLabel.setText(currentDestinationName);

        colorStars(this.currentDestination.getRating());

        if (this.currentDestination.getComment() != null) {
            commentTextField.setText(this.currentDestination.getComment());
        }

        updateListView();
    }

}
