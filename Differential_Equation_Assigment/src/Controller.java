import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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

    private double x0, y0, X, step;

    private Model model = new Model();

    @FXML
    void onComputeClick() throws IOException {
        x0 = Double.parseDouble(initXTextField.getText());
        y0 = Double.parseDouble(initYTextField.getText());
        X = Double.parseDouble(intervalEndTextField.getText());
        step = Double.parseDouble(stepTextField.getText());

        model.plotOther(x0, y0, X, step);

    }

}
