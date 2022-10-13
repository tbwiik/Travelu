package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;
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

    private String destinationListFile;
    private String currentDestinationFile;

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
    private void initialize() throws FileNotFoundException {

        destinationListFile = "DestinationList.json";
        currentDestinationFile = "CurrentDestination.json";

        this.destinationList = traveluHandler.readDestinationListJSON();
        String currentDestinationName = traveluHandler.readCurrentDestinationNameJSON();

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

        destinationLabel.setText(currentDestinationName);

        if (this.currentDestination.getComment() != null) {
            commentTextField.setText(this.currentDestination.getComment());
        }

        updateListView();

    }

    /**
     * updates view of activity list
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
     * add activity to the list of activities, and update listview
     * 
     * @throws IOException in case of filehandling issue
     */
    @FXML
    private void handleAddActivity() throws IOException {
        String activity = newActivityTextField.getText();
        if (activity.isBlank() || activity == null)
            return;

        try {
            currentDestination.addActivity(activity);
        } catch (Exception e) {
            // TODO: give relevant user feedback here
            System.out.println("Invalid activity input");
        }

        writeChanges();
        updateListView();
        newActivityTextField.setText("");

    }

    /**
     * updates changes to currentDestination, and writes these to json.
     * 
     * @throws IOException in case of filehandling issue
     */
    private void writeChanges() throws IOException {
        this.destinationList.updateDestination(currentDestination);

        traveluHandler.writeJSON(this.destinationList, destinationListFile);
    }

    @FXML
    private void handleSelectFile() {
        System.out.println("Select file");
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
        System.out.println("Set arrival date");
    }

    @FXML
    private void handleSetDepartureDate() {
        System.out.println("Set departure date");
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

    public ListView<String> getActivitiesListView() {
        return activitiesListView;
    }

    public void initializeFromTestFiles() throws FileNotFoundException {

        destinationListFile = "testDestinationList.json";
        currentDestinationFile = "testCurrentDestinationName.json";

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