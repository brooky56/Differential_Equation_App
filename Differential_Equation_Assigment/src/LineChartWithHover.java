import javafx.application.Application;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;

/**
 * Displays a LineChart which displays the value of a plotted Node when you hover over the Node.
 */
public class LineChartWithHover extends Application {
    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) {
        Model m = new Model();


        final LineChart lineChart = new LineChart(
                new NumberAxis(), new NumberAxis(),
                FXCollections.observableArrayList(
                        new XYChart.Series(
                                "My portfolio",
                                FXCollections.observableArrayList(
                                        plot(m.arrX, m.arrY)
                                )
                        )
                )
        );


        lineChart.setCursor(Cursor.CROSSHAIR);

        lineChart.setTitle("Stock Monitoring, 2013");

        stage.setScene(new Scene(lineChart, 500, 400));
        stage.show();
    }

    /**
     * @return plotted y values for monotonically increasing integer x values, starting from x=1
     */
    public ObservableList<XYChart.Data<Double, Double>> plot(ArrayList<Double> arrayListX, ArrayList<Double> arrayListY) {
        final ObservableList<XYChart.Data<Double, Double>> dataset = FXCollections.observableArrayList();
        int i = 0;
        while (i < arrayListY.size()) {
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(arrayListX.get(i), arrayListY.get(i));
            data.setNode(
                    new HoveredThresholdNode(arrayListX.get(i), arrayListY.get(i))
            );

            dataset.add(data);
            i++;
        }

        return dataset;
    }


    class HoveredThresholdNode extends StackPane {

        HoveredThresholdNode(double priorValue, double value) {
            setPrefSize(15, 15);

            final Label label = createDataThresholdLabel(priorValue, value);

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
                }
            });
        }

        private Label createDataThresholdLabel(double priorValue, double value) {
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

    public static void main(String[] args) {
        launch(args);
    }
}