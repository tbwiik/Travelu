package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    Label commentFeedbackLabel;

    @FXML
    Label activityFeedbackLabel;

    @FXML
    private void initialize() throws FileNotFoundException, IOException {

        destinationListFile = "DestinationList.json";

        this.destinationList = traveluHandler.readDestinationListJSON();
        String currentDestinationName = traveluHandler.readCurrentDestinationNameJSON();

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

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
            iae.printStackTrace();
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
            currentDestination.removeActivity(currentActivity);
            updateListView();
            try {
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
     * Changes comment, and writes this to file
     * 
     * @throws IOException in case of filehandling issue
     */
    @FXML
    private void handleChangeComment() {
        String newComment = commentTextField.getText();

        if (newComment.isBlank()) {
            commentFeedbackLabel.setText("Add comment to update.");
        }

        currentDestination.setComment(newComment);
        if (!newComment.isBlank()) {
            commentFeedbackLabel.setText("Comment updated!");
        }

        try {
            writeChanges();
        } catch (IOException ioe) {
            ioe.printStackTrace();
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

    public void initializeFromTestFiles() throws FileNotFoundException, IOException {

        destinationListFile = "testDestinationList.json";

        this.destinationList = traveluHandler.readDestinationListJSON("testDestinationList.json");
        String currentDestinationName = traveluHandler
                .readCurrentDestinationNameJSON("testCurrentDestinationName.json");

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

        destinationLabel.setText(currentDestinationName);

        if (this.currentDestination.getComment() != null) {
            commentTextField.setText(this.currentDestination.getComment());
        }

        updateListView();
    }

}