package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    ListView<String> activitiesListView;

    @FXML
    TextField newActivityTextField;

    @FXML
    TextField commentTextField;

    @FXML
    Label commentUpdatedFeedbackLabel;

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

    // TODO: Is throws IOException really needed here? Same for remove activity
    /**
     * Adds activity to the list of activities, and updates activitiesListView
     * 
     * 
     * @throws IOException in case of filehandling issue
     */
    @FXML
    private void handleAddActivity() throws IOException {
        String activity = newActivityTextField.getText();
        if (activity.isBlank())
            return;

        try {
            currentDestination.addActivity(activity);
        } catch (Exception e) {
            // TODO: give relevant user feedback here
        }

        writeChanges();
        updateListView();
        newActivityTextField.setText("");

    }

    /**
     * Removes activity from list of activities, and updates activitiesListView
     */
    @FXML
    private void handleRemoveActivity() {
        if (currentActivity != null) {
            currentDestination.removeActivity(currentActivity);
            updateListView();
            try {
                writeChanges();
            } catch (Exception e) {
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
     * call method update star with parameter 1 based on which star clicked
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
     * @param rating
     */
    private void colorStars(int starNumber) {

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
     */
    @FXML
    private void handleChangeComment() {
        String newComment = commentTextField.getText();
        // if there is no comment. TODO: Give feedback to user
        if (newComment.isBlank())
            return;

        currentDestination.setComment(newComment);
        try {
            writeChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSetArrivalDate() {

        String arrivalDate = arrivalDatePicker.getEditor().getText();
        String departureDate = departureDatePicker.getEditor().getText();

        arrivalDateLabel.setText(arrivalDate);

        if (departureDate.isBlank()) {
            currentDestination.setDateInterval(arrivalDate, arrivalDate);
        } else {
            currentDestination.setDateInterval(arrivalDate, departureDate);
        }

    }

    @FXML
    private void handleSetDepartureDate() {

        String arrivalDate = arrivalDatePicker.getEditor().getText();
        String departureDate = departureDatePicker.getEditor().getText();

        arrivalDateLabel.setText(arrivalDate);

        if (arrivalDate.isBlank()) {
            currentDestination.setDateInterval(departureDate, departureDate);
        } else {
            currentDestination.setDateInterval(arrivalDate, departureDate);
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