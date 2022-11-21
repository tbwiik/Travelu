package travelu.fxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App.
 */
public final class App extends Application {

    /**
     * Scene object.
     */
    private static Scene scene;

    /**
     * Starts application.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        final int width = 754;
        final int height = 920;
        scene = new Scene(loadFXML("destinationList"), width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets root.
     * @param fxml
     * @throws IOException
     */
    static void setRoot(final String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads FXML view.
     * @param fxml
     * @return Parent - loaded fxmlloader
     * @throws IOException
     */
    private static Parent loadFXML(final String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Main method, launches application.
     * @param args
     */
    public static void main(final String[] args) {
        launch();
    }

}
