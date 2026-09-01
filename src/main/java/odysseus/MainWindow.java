package odysseus;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/** Controls the JavaFX conversation view for Odysseus. */
public class MainWindow {
    private static final String WELCOME_MESSAGE = "Ahoy, traveler. I keep the small obligations that"
            + " would otherwise drift away. Tell me what belongs in the ship's log.";
    private static final Color ROUTE_COLOR = Color.web("#A7442B");

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Canvas voyageLine;
    @FXML
    private VBox masthead;

    private Odysseus odysseus;

    /** Sets up automatic scrolling and the masthead's voyage route. */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener((observable, oldHeight, newHeight) -> scrollPane.setVvalue(1.0));
        voyageLine.widthProperty().addListener((observable, oldWidth, newWidth) -> drawVoyageLine());
        voyageLine.widthProperty().bind(masthead.widthProperty().subtract(84));
        drawVoyageLine();
    }

    /** Injects the chatbot that answers commands entered in this window. */
    public void setOdysseus(Odysseus odysseus) {
        this.odysseus = odysseus;
    }

    /** Shows the first Odysseus message once the chatbot has been injected. */
    public void showWelcome() {
        addOdysseusDialog(WELCOME_MESSAGE);
        userInput.requestFocus();
    }

    /** Adds the traveler command and Odysseus response to the conversation. */
    @FXML
    private void handleUserInput() {
        String command = userInput.getText().trim();
        if (command.isEmpty()) {
            return;
        }
        dialogContainer.getChildren().add(DialogBox.getUserDialog(command));
        addOdysseusDialog(odysseus.getResponse(command));
        userInput.clear();
    }

    /** Adds a left-aligned Odysseus entry to the ship's log. */
    private void addOdysseusDialog(String message) {
        dialogContainer.getChildren().add(DialogBox.getOdysseusDialog(message));
    }

    /** Draws the intentionally imperfect route line in the masthead. */
    private void drawVoyageLine() {
        double width = voyageLine.getWidth();
        GraphicsContext graphics = voyageLine.getGraphicsContext2D();
        graphics.clearRect(0, 0, width, voyageLine.getHeight());
        graphics.setStroke(ROUTE_COLOR);
        graphics.setFill(ROUTE_COLOR);
        graphics.setLineWidth(1.1);
        graphics.beginPath();
        for (int position = 0; position <= width; position += 4) {
            double height = 11 + Math.sin(position / 31) * 3 + Math.sin(position / 11) * 0.8;
            if (position == 0) {
                graphics.moveTo(position, height);
            } else {
                graphics.lineTo(position, height);
            }
        }
        graphics.stroke();
        graphics.fillOval(0, 7, 8, 8);
        graphics.fillOval(Math.max(0, width - 8), 7, 8, 8);
    }
}
