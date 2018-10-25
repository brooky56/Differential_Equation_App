import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {

    @FXML
    private Label initXLabel;

    @FXML
    private TextField initXTextField;

    @FXML
    private Button computePlotButton;

    @FXML
    private VBox workVBox;

    @FXML
    private Pane rootPane;

    @FXML
    private Label initYLabel;

    @FXML
    private TextField stepTextField;

    @FXML
    private TextField initYTextField;

    @FXML
    private Label intervalEndLabel;

    @FXML
    private TextField intervalEndTextField;

    @FXML
    private Label stepLabel;

    private Model model = new Model();

    @FXML
    void onComputeClick() throws IOException {
        double x0 = Double.parseDouble(initXTextField.getText());
        double y0 = Double.parseDouble(initYTextField.getText());
        double x = Double.parseDouble(intervalEndTextField.getText());
        double step = Double.parseDouble(stepTextField.getText());

        model.plotEM(x0, y0, x, step);
        model.plotImEM(x0, y0, x, step);
        model.plotRKM(x0, y0, x, step);

        Logs logs = new Logs();
        logs.close();
    }
}
