import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

class LineChartHover {

    private String methodName;
    private LineChart lineChart;

    LineChartHover(ArrayList<Double> arrayX, ArrayList<Double> arrayY, String methodName) {
        this.methodName = methodName;
        this.lineChart = new LineChart(new NumberAxis(), new NumberAxis(),
                FXCollections.observableArrayList(
                        new XYChart.Series(
                                methodName,
                                FXCollections.observableArrayList(
                                        plot(arrayX, arrayY)
                                )
                        )
                )
        );
    }

    public LineChart getLineChart() {
        return lineChart;
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

        HoveredThresholdNode(double xValue, double yValue) {
            setPrefSize(10, 10);

            final Label label = createDataThresholdLabel(xValue, yValue);

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

        private Label createDataThresholdLabel(double xValue, double yValue) {
            return getLabel(xValue, yValue);
        }

        static Label getLabel(double xValue, double yValue) {
            NumberFormat nf = new DecimalFormat("#0.000");

            final Label label = new Label(nf.format(xValue) + " " + nf.format(yValue));
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");

            if (xValue == 0) {
                label.setTextFill(Color.DARKGRAY);
            } else if (yValue > xValue) {
                label.setTextFill(Color.FORESTGREEN);
            } else {
                label.setTextFill(Color.FIREBRICK);
            }

            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }
}
