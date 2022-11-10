package travelu.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class DestinationController {

    // currently selected destination
    private Destination currentDestination;
    private DestinationList destinationList;
    private TraveluHandler traveluHandler = new TraveluHandler();

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
    Label commentUpdatedFeedbackLabel;

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

        arrivalDateLabel.setText(currentDestination.getDateInterval().getArrivalDate());
        departureDateLabel.setText(currentDestination.getDateInterval().getDepartureDate());

        // Standardizes date formatting in datePicker. Largely copied from documentation for datePicker.setconverter
        StringConverter<LocalDate> stringConverter = new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            {
                arrivalDatePicker.setPromptText(pattern.toLowerCase());
                departureDatePicker.setPromptText(pattern.toLowerCase());
            }
            // TODO: Fix these
            @Override public String toString(LocalDate date) {
                try{
                    if (date != null) {
                        return formatter.format(date);
                    } else {
                        return "";
                    }}catch(Exception e){
                        return "";}
            } 
            @Override public LocalDate fromString(String string) {
                try{
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, formatter);
                    } else {
                        return null;
                    }
                }
                catch(Exception e){
                    return null;
                }

            }
        };
        
        arrivalDatePicker.setConverter(stringConverter);
        departureDatePicker.setConverter(stringConverter);
        

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

    /**
     * Sets arrival date, and catches exceptions due to date validation errors
     */
    @FXML
    private void handleSetArrivalDate() {
        String arrivalDate = arrivalDatePicker.getValue() == null ? "" : arrivalDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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

        String departureDate = departureDatePicker.getValue() == null ? "" : departureDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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
