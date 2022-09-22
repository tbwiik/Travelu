package gr2219;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr2219.backend.Destination;
import gr2219.backend.DestinationList;
import gr2219.backend.TraveluHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PrimaryController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private TextArea destinationText;

    @FXML
    private Text feedbackText;

    private DestinationList destinationList = new DestinationList();

    private String currentDestination;

    private TraveluHandler traveluHandler = new TraveluHandler();

    @FXML
    private void initialize() throws FileNotFoundException {

        // add destinations from persistence file
        for (Destination destination : traveluHandler.readJSON().getList()) {
            destinationList.addDestination(destination);
        }

        listView.setStyle("-fx-font-size:20;");

        // add all destinations to the list-view
        listView.getItems()
                .addAll(destinationList.getDestinationNames());

        // make currentDestination the selected list-view item
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentDestination = listView.getSelectionModel().selectedItemProperty().getValue();
            }
        });

        // make double-click on list-view item take you to page with currentDestination
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    try {
                        switchToSecondary(currentDestination);
                    } catch (IOException e) {
                        // TODO create appropriate exception
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    private void switchToSecondary(String destinationName) throws IOException {

        // Succesfully selected this destination
        System.out.println("Selected " + destinationName);

        App.setRoot("destination");

    }

    public String getCurrentDestination() {
        return currentDestination;
    }

    @FXML
    public void addDestination() throws IOException {

        String newDestinationName = destinationText.getText();
        if (newDestinationName.isBlank()) {
            // if user didn't input any text
            // remove any feedback given and do nothing
            feedbackText.setText("");
        } else if (destinationList.containsDestination(newDestinationName.toLowerCase())) {
            // if the input text matches any of the already registrations
            // give feedback
            feedbackText.setText("You have already registered this destination");
        } else {
            // if everything is ok with the input
            // create new destination with input as name
            Destination newDestination = new Destination(newDestinationName.strip(), null, 0, null,
                    currentDestination);

            // add destination to list-view and destinations
            listView.getItems().add(newDestination.getName());
            destinationList.addDestination(newDestination);

            // remove any feedback given
            feedbackText.setText("");

            // remove text in inputField
            destinationText.setText((""));
        }
        traveluHandler.writeJSON(destinationList);
        
    }

    @FXML
    public void removeDestination() throws IOException {
        if (currentDestination == null) {
            // if there is no selected destination
            // give user feedback
            feedbackText.setText("Please select a destination you would like to remove");
        } else {
            // if there is a selected destination
            // remove the selected destination from destinations and list-view
            destinationList.removeDestination(currentDestination);
            listView.getItems().remove(currentDestination);
        }
        traveluHandler.writeJSON(destinationList);
    }

}
