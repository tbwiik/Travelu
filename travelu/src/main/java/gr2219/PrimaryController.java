package gr2219;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        destinations.add(new Destination("Spain", 3, "a horrible place"));
        destinations.add(new Destination("France", 10, "a unknown diamond"));
        destinations.add(new Destination("Italy", 6, "something for everyone"));
        destinations.add(new Destination("Turkey", 2, "never again"));
        destinations.add(new Destination("Sweden", 1, "worse than imaginable"));

        // add all destinations to the list-view
        listView.getItems().addAll(destinations.stream().map(destination -> destination.getDestination()).toList());

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
                .filter(destination -> destination.getDestination().toLowerCase()
                        .equals(destinationText.getText().strip().toLowerCase()))
                .findAny().isPresent()) {
            // if the input text matches any of the already registrations
            // give feedback
            feedbackText.setText("You have already registered this destination");
        } else {
            // if everything is ok with the input
            // create new destination with input as name
            Destination newDestination = new Destination(destinationText.getText().strip(), 0, null);

            // add destination to list-view and destinations
            listView.getItems().add(newDestination.getDestination());
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
                    .filter(destination -> destination.getDestination().toLowerCase()
                            .equals(currentDestination.strip().toLowerCase()))
                    .findAny().orElseThrow());
            listView.getItems().remove(currentDestination);
        }
    }

}
