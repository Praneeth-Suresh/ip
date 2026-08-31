package odysseus;

import javafx.application.Application;

/** A launcher class to work around JavaFX classpath issues. */
public class Launcher {
    /** Starts the HelloWorld JavaFX application. */
    public static void main(String[] args) {
        Application.launch(HelloWorld.class, args);
    }
}
