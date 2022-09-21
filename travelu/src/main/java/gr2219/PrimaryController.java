package gr2219;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr2219.backend.Destination;
import gr2219.backend.DestinationList;
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
    

    @FXML
    private void initialize() {
        // add destinations to destinationList
        destinationList.addDestination(new Destination("Spain", null, 3, null, "a horrible place"));
        destinationList.addDestination(new Destination("France", null, 10, null, "a unknown diamond"));
        destinationList.addDestination(new Destination("Italy", null, 6, null, "something for everyone"));
        destinationList.addDestination(new Destination("Turkey", null, 2, null, "never again"));
        destinationList.addDestination(new Destination("Sweden", null, 1, null, "worse than imaginable"));

        listView.setStyle("-fx-font-size:20;");

        // add all destinations to the list-view
        listView.getItems().addAll(destinationList.getList().stream().map(destination -> destination.getName()).toList());

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

        System.out.println("Selected " + destinationName);

        App.setRoot("destination");

    }

    public String getCurrentDestination() {
        return currentDestination;
    }

    @FXML
    public void addDestination() {
        if (destinationText.getText().isBlank()) {
            // if user didn't input any text
            // remove any feedback given and do nothing
            feedbackText.setText("");
        } else if (destinationList.getList().stream()
                .filter(destination -> destination.getName().toLowerCase()
                        .equals(destinationText.getText().strip().toLowerCase()))
                .findAny().isPresent()) {
            // if the input text matches any of the already registrations
            // give feedback
            feedbackText.setText("You have already registered this destination");
        } else {
            // if everything is ok with the input
            // create new destination with input as name
            Destination newDestination = new Destination(destinationText.getText().strip(), null, 0, null, currentDestination);

            // add destination to list-view and destinations
            listView.getItems().add(newDestination.getName());
            destinationList.addDestination(newDestination);

            // remove any feedback given
            feedbackText.setText("");

            // remove text in inputField
            destinationText.setText((""));
        }
    }

    @FXML
    public void removeDestination() {
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
    }

}
