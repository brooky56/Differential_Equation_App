import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Model {

    private ArrayList<Double> arrX = new ArrayList<>();
    private ArrayList<Double> arrY = new ArrayList<>();

    private static XYChart.Series EM = new XYChart.Series();

    private static FileWriter writer;

    void plotOther(double x0, double y0, double X, double step) throws IOException {
        int m = (int) ((X - x0) / step); //separation times

        writer = new FileWriter("output.txt");

        EM(step, m, x0, y0);

        final LineChart lineCh = new LineChart(new NumberAxis(), new NumberAxis(),
                FXCollections.observableArrayList(
                        new XYChart.Series(
                                "Euler Method",
                                FXCollections.observableArrayList(
                                        plot(arrX, arrY)
                                )
                        )
                )
        );

        lineCh.getData().add(EM);

        arrX.clear();
        arrY.clear();
        writer.write("---------------------------------------------------------------------------------------------------\n");

        writer.flush();
        writer.close();

        lineCh.setCursor(Cursor.CROSSHAIR);
        lineCh.setTitle("Differential equations");

        Scene scene = new Scene(lineCh);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setFullScreen(true);
        newStage.show();
    }

    private ObservableList<XYChart.Data<Double, Double>> plot(ArrayList<Double> arrListX, ArrayList<Double> arrListY) {
        final ObservableList<XYChart.Data<Double, Double>> dataSet = FXCollections.observableArrayList();
        int i = 0;
        while (i < arrListY.size()) {
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(arrListX.get(i), arrListY.get(i));
            data.setNode(
                    new HoveredThresholdNode(arrListX.get(i), arrListY.get(i))
            );

            dataSet.add(data);
            i++;
        }

        return dataSet;
    }

    static class HoveredThresholdNode extends StackPane {

        HoveredThresholdNode(double priorValue, double value) {
            setPrefSize(15, 15);

            final Label label = createDataThresholdLabel(priorValue, value);

            setOnMouseEntered(mouseEvent -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            setOnMouseExited(mouseEvent -> {
                getChildren().clear();
                setCursor(Cursor.CROSSHAIR);
            });
        }

        private Label createDataThresholdLabel(double priorValue, double value) {
            return getLabel(priorValue, value);
        }

        static Label getLabel(double priorValue, double value) {
            final Label label = new Label(value + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

            if (priorValue == 0) {
                label.setTextFill(Color.DARKGRAY);
            } else if (value > priorValue) {
                label.setTextFill(Color.FORESTGREEN);
            } else {
                label.setTextFill(Color.FIREBRICK);
            }

            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }

    private void EM(double step, int m, double initialX, double initialY) throws IOException {

        arrX.add(initialX);
        arrY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrX.add(myEulerX(step, arrX.get(i - 1)));
        }

        for (int i = 1; i < m + 1; i++) {
            arrY.add(myEulerY(step, arrX.get(i - 1), arrY.get(i - 1)));
        }
        writer.write("EM_X: " + arrX.toString() + "\n");
        writer.write("EM_Y: " + arrY.toString() + "\n");
    }

    private static double myEulerX(double h, double xPre) {

        return xPre + h;
    }

    private static double myEulerY(double h, double xPre, double yPre) {

        return yPre + h * (myFunction(xPre, yPre));
    }

    private static double myFunction(double x, double y) {

        return 4 / (x * x) - y / x - y * y;
    }
}
