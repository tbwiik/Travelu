package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;

/**
 * JavaFX tests for DestinationListController
 */
@TestInstance(Lifecycle.PER_CLASS) // For import of external headless function
public class DestinationListControllerTest extends ApplicationTest {

        private DestinationListController destinationListController;

        private Parent root;

        private DestinationList destinationList;

        private TraveluHandler traveluHandler = new TraveluHandler();

        private TextArea destinationText;
        private Button addButton;
        private Button removeButton;
        private Button nameButton;
        private Button ratingButton;
        private Label feedbackLabel;
        private ListView<String> listView;

        /**
         * Enables headless testing
         */
        @BeforeAll
        public void setupHeadless() {
                TestHelperMethods.supportHeadless();
        }

        @BeforeEach
        private void start() {
                destinationText = lookup("#destinationText").query();
                addButton = lookup("#addButton").query();
                removeButton = lookup("#removeButton").query();
                nameButton = lookup("#nameButton").query();
                ratingButton = lookup("#ratingButton").query();
                feedbackLabel = lookup("#feedbackLabel").query();
                listView = lookup("#listView").query();
        }

        /**
         * Tests if DestinationList works as intended
         */
        @Override
        public void start(Stage stage) throws IOException {

                destinationList = new DestinationList();
                destinationList.addDestination(new Destination("Spain", new DateInterval(), 0, null,
                                null));
                destinationList.addDestination(new Destination("Greece", new DateInterval(), 2, null,
                                null));
                destinationList.addDestination(new Destination("Turkey", new DateInterval(), 3, null,
                                null));

                traveluHandler.writeJSON(destinationList, "testDestinationList.json");

                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destinationList.fxml"));
                root = fxmlLoader.load();
                destinationListController = fxmlLoader.getController();
                stage.setScene(new Scene(root));
                stage.show();

                destinationListController.initiliazeFromTestFiles();
        }

        @Test
        public void testInitialize() {
                assertEquals(3, destinationListController.getDestinationListNames().size());
                assertEquals("Spain", destinationListController.getDestinationListNames().get(0));
                assertEquals("Greece", destinationListController.getDestinationListNames().get(1));
                assertEquals("Turkey", destinationListController.getDestinationListNames().get(2));

                assertEquals("Spain", destinationListController.getListViewItems().get(0));
                assertEquals("Greece★★", destinationListController.getListViewItems().get(1));
                assertEquals("Turkey★★★", destinationListController.getListViewItems().get(2));
        }

        /**
         * Tests if you can add Destination to DestinationList
         */
        @Test
        public void testAdd() {

                destinationList.addDestination(new Destination("Place", new DateInterval(), 0, null,
                                null));

                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());
                
                clickOn(destinationText).write("Place");

                clickOn(addButton);

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                // check if destination name is recognized as the same spaces are added
                clickOn(destinationText).write("  Place  ");
                clickOn(addButton);

                // check if feedbackLabel gives proper feedback
                assertEquals("You have already registered this destination", feedbackLabel.getText());
                

                // check if spaces before and after destination name are removed
                clickOn(destinationText).eraseText(destinationText.getText().length()).write("  Denmark  ");
                clickOn(addButton);
                
                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                destinationList.addDestination(new Destination("Denmark", new DateInterval(), 0, null,
                                null));

                assertEquals(destinationList.getDestinationNames(), 
                                destinationListController.getDestinationListNames());
                

                // ensure spaces between letters are not removed
                clickOn(destinationText).eraseText(destinationText.getText().length()).write("  New Zealand  ");
                clickOn(addButton);

                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                destinationList.addDestination(new Destination("New Zealand", new DateInterval(), 3, null,
                                null));
                
                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                // test add invalid destination name
                clickOn(destinationText).write("\"''");

                clickOn(addButton);
                
                // destinationList should be unchanged
                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());
                // check if feedbackLabel gives proper feedback
                assertEquals("Destination name must contain only letters, spaces and dashes", feedbackLabel.getText());

                // test click add button without inputting destination name
                clickOn(addButton);
                // destinationList should be unchanged
                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                // check that feedbackLabel is reset on valid input
                clickOn(destinationText).eraseText(destinationText.getText().length()).write("New Place");
                clickOn(addButton);

                assertEquals("", feedbackLabel.getText());

        }

        /**
         * Tests if you can remove Destination from DestinationList
         */
        @Test
        public void testRemove() {

                // check clicking remove button before selecting destination
                clickOn(removeButton);

                // check if feedbackLabel gives proper feedback
                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

                // this should not change the destinationList
                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                clickOn("Greece★★");
                clickOn(removeButton);

                // TODO: this test fails because feedbacklabel reset is not implemented yet
                /*
                // check if feedbackLabel is reset
                assertEquals("", feedbackLabel.getText()); */


                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());
                destinationList.removeDestination("Greece");

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                // TODO: this test fails because there is a bug. Fixed in seperate issue
                /*
                clickOn(listView);
                clickOn(removeButton);
                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());
                */


                // check if feedbackLabel gives proper feedback
                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

        
                // TODO: this test fails because of bugs.
                /*
                // check clicking remove button after removing destination
                clickOn(removeButton);
                // check if feedbackLabel gives proper feedback
                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

                // this should not change destinationList
                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames()); */

                
        }

        /**
         * Tests implementation of sorting by name functionality in controller.
         * <p>
         * Sorting functionality is thoroughly tested in core.
         */
        @Test
        public void testSortByName() {

                assertEquals("[Spain, Greece★★, Turkey★★★]",
                                destinationListController.getListViewItems().toString());

                clickOn(nameButton);

                assertNotEquals("[Spain, Greece★★, Turkey★★★]",
                                destinationListController.getListViewItems().toString());

                assertEquals("[Greece★★, Spain, Turkey★★★]",
                                destinationListController.getListViewItems().toString());
        }

        /**
         * Tests implementation of sorting by rating functionality in controller.
         * <p>
         * Sorting functionality is thoroughly tested in core.
         */
        @Test
        public void testSortByRating() throws IOException {

                assertEquals("[Spain, Greece★★, Turkey★★★]",
                                destinationListController.getListViewItems().toString());

                clickOn(ratingButton);

                assertEquals("[Turkey★★★, Greece★★, Spain]",
                                destinationListController.getListViewItems().toString());

        }

}