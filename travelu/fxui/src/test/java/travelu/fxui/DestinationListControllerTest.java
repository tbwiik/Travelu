package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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

        private Parent root;

        private DestinationList destinationList;

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

}