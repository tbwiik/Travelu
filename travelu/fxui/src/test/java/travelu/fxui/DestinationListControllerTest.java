package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.testfx.matcher.control.LabeledMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;

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
        }

        /**
         * Tests if DestinationList works as intended
         */
        @Override
        public void start(Stage stage) throws IOException {

                destinationList = new DestinationList();
                destinationList.addDestination(new Destination("Spain", null, 2, null,
                                null));
                destinationList.addDestination(new Destination("Greece", null, 2, null,
                                null));
                destinationList.addDestination(new Destination("Turkey", null, 3, null,
                                null));

                traveluHandler.writeJSON(destinationList, "testDestinationList.json");

                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destinationList.fxml"));
                root = fxmlLoader.load();
                destinationListController = fxmlLoader.getController();
                stage.setScene(new Scene(root));
                stage.show();

                destinationListController.initiliazeFromTestFiles();
        }

        /**
         * Tests if you can add Destination to DestinationList
         */
        @Test
        public void testAdd() {

                destinationList.addDestination(new Destination("Place", null, null, null,
                                null));

                clickOn(destinationText).write("Place");

                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                clickOn(addButton);

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

        }

        /**
         * Tests if you can remove Destination from DestinationList
         */
        @Test
        public void testRemove() {

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                clickOn("Greece");
                clickOn(removeButton);

                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                destinationList.removeDestination("Greece");

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());
        }

}