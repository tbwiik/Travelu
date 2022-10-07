package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    ListView<String> plannedActivitiesListView;

    @FXML
    TextField newActivityTextField;

    @FXML
    TextField commentTextField;

    @FXML
    Label commentUpdatedFeedbackLabel;

    @FXML
    private void initialize() throws FileNotFoundException {

        this.destinationList = traveluHandler.readDestinationListJSON();
        String currentDestinationName = traveluHandler.readCurrentDestinationNameJSON();
        System.out.println(currentDestinationName);

        this.currentDestination = this.destinationList.getDestinationCopyByName(currentDestinationName);

        destinationLabel.setText(currentDestinationName);

        if(this.currentDestination.getComment() != null){
            commentTextField.setText(this.currentDestination.getComment());
        }

        updateListView();

    }

    @FXML
    private void updateListView(){
        plannedActivitiesListView.getItems().clear();
        plannedActivitiesListView.getItems().addAll(this.currentDestination.getActivities());
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
     * 
     */
    @FXML
    private void handleAddActivity() throws IOException {
        String activity = newActivityTextField.getText();
        if(activity.isBlank() || activity == null) return;

        currentDestination.addActivity(activity);

        writeChanges();
        updateListView();

    }

    /**
     * 
     * @throws IOException
     */
    private void writeChanges() throws IOException{
        this.destinationList.removeDestination(this.currentDestination.getName());
        this.destinationList.addDestination(currentDestination);

        traveluHandler.writeJSON(this.destinationList, "DestinationList.json");
    }

    @FXML
    private void handleSelectFile() {
        System.out.println("Select file");
    }

    @FXML
    private void handleChangeComment() {
        System.out.println("Change Comment");
    }

    @FXML
    private void handleSetArrivalDate() {
        System.out.println("Set arrival date");
    }

    @FXML
    private void handleSetDepartureDate() {
        System.out.println("Set departure date");
    }

    public String getDestination() {
        return currentDestination.getName();
    }

}