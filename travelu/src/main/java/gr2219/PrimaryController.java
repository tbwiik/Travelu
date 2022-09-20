package gr2219;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr2219.backend.Destination;
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

    private List<Destination> destinations = new ArrayList<>();

    private String currentDestination;

    @FXML
    private void initialize() {
        // add destinations to destinations
        destinations.add(new Destination("Spain", null, 3, null, "a horrible place"));
        destinations.add(new Destination("France", null, 10, null, "a unknown diamond"));
        destinations.add(new Destination("Italy", null, 6, null, "something for everyone"));
        destinations.add(new Destination("Turkey", null, 2, null, "never again"));
        destinations.add(new Destination("Sweden", null, 1, null, "worse than imaginable"));

        // add all destinations to the list-view
        listView.getItems().addAll(destinations.stream().map(destination -> destination.getName()).toList());

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
                        switchToSecondary();
                    } catch (IOException e) {
                        // TODO create appropriate exception
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
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
        } else if (destinations.stream()
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
            destinations.add(newDestination);

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
            destinations.remove(destinations.stream()
                    .filter(destination -> destination.getName().toLowerCase()
                            .equals(currentDestination.strip().toLowerCase()))
                    .findAny().orElseThrow());
            listView.getItems().remove(currentDestination);
        }
    }

}
