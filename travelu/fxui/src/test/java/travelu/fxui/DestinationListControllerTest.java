package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import org.testfx.matcher.control.LabeledMatchers;
import org.junit.jupiter.api.Test;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;

public class DestinationListControllerTest extends ApplicationTest {

        private DestinationListController destinationListController;

        private Parent root;

        private DestinationList destinationList;

        private TraveluHandler traveluHandler = new TraveluHandler();

        private TextArea textArea;

        @BeforeEach
        private void start() {
                textArea = lookup("#destinationText").query();
        }

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

                System.out.println("Run testmethod");
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destinationList.fxml"));
                root = fxmlLoader.load();
                destinationListController = fxmlLoader.getController();
                stage.setScene(new Scene(root));
                stage.show();

                destinationListController.initiliazeFromTestFiles();
        }

        @Test
        public void testAdd() {

                destinationList.addDestination(new Destination("Place", null, null, null,
                                null));

                clickOn(textArea).write("Place");

                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                clickOn("Add");

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

        }

        @Test
        public void testRemove() {

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                clickOn("Greece");
                clickOn("Remove");

                assertNotEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());

                destinationList.removeDestination("Greece");

                assertEquals(destinationList.getDestinationNames(),
                                destinationListController.getDestinationListNames());
        }

}