package odysseus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/** A minimal JavaFX application that shows a Hello World message. */
public class HelloWorld extends Application {
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        StackPane root = new StackPane(helloWorld);
        Scene scene = new Scene(root, 320, 240);

        stage.setTitle("Odysseus");
        stage.setScene(scene);
        stage.show();
    }

    /** Launches the JavaFX application. */
    public static void main(String[] args) {
        launch(args);
    }
}
