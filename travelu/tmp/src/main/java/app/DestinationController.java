package app;

import java.io.IOException;

import app.core.Destination;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DestinationController {

    // currently selected destination
    private Destination currentDestination;

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

    /**
     * Returns to destination-list
     * 
     * @throws IOException
     */
    @FXML
    private void handleReturnButton() throws IOException {
        App.setRoot("destinationList");
    }

    @FXML
    private void handleAddActivity() {
        System.out.println("Add activity");
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