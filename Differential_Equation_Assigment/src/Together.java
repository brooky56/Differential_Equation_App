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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

class Together {
    public static ArrayList<Double> arrayListX = new ArrayList<>();
    public static ArrayList<Double> arrayListY = new ArrayList<>();

    public static XYChart.Series EM = new XYChart.Series();
    public static XYChart.Series IEM = new XYChart.Series();
    public static XYChart.Series RKM = new XYChart.Series();
    public static XYChart.Series realF = new XYChart.Series();

    public static FileWriter writer;

    void plotAll() throws IOException {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<>(x, y);
        numberLineChart.setTitle("Integrable curve");

        double a = 1.0, b = 4.0; //boards of interval
        double h = 0.1; //step
        int m = (int) ((b - a) / h); //separation times

        writer = new FileWriter("output_together.txt");

        EM(h, m, 1.0, 3.0);
        numberLineChart.getData().add(EM);
        arrayListX.clear();
        arrayListY.clear();
        writer.write("---------------------------------------------------------------------------------------------------\n");
        IEM(h, m, 1.0, 3.0);
        numberLineChart.getData().add(IEM);
        arrayListX.clear();
        arrayListY.clear();
        writer.write("---------------------------------------------------------------------------------------------------\n");
        RKM(h, m, 1.0, 3.0);
        numberLineChart.getData().add(RKM);
        arrayListX.clear();
        arrayListY.clear();
        writer.write("---------------------------------------------------------------------------------------------------\n");
        //realFunc(2.0, 1.0);
        //numberLineChart.getData().add(realF);


        writer.flush();
        writer.close();

        Scene scene = new Scene(numberLineChart, 1200, 720);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void EM(double h, int m, double initialX, double initialY) throws IOException {


        EM.setName("Euler Method");

        arrayListX.add(initialX);
        arrayListY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrayListX.add(myEulerX(h, arrayListX.get(i - 1)));
        }

        for (int i = 1; i < m + 1; i++) {
            arrayListY.add(myEulerY(h, arrayListX.get(i - 1), arrayListY.get(i - 1)));
        }


        writer.write("EM_X: " + arrayListX.toString() + "\n");
        writer.write("EM_Y: " + arrayListY.toString() + "\n");

        EM.setData(plot(arrayListX, arrayListY));

    }

    public static void IEM(double h, int m, double initialX, double initialY) throws IOException {


        IEM.setName("Improved Euler Method");

        arrayListX.add(initialX);
        arrayListY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrayListX.add(myIEulerX(h, arrayListX.get(i - 1)));
        }

        for (int i = 1; i < m + 1; i++) {
            arrayListY.add(myIEulerY(h, arrayListX.get(i - 1), arrayListY.get(i - 1)));
        }

        writer.write("IEM_X: " + arrayListX.toString() + "\n");
        writer.write("IEM_Y: " + arrayListY.toString() + "\n");

        IEM.setData(plot(arrayListX, arrayListY));

    }

    public static void RKM(double h, int m, double initialX, double initialY) throws IOException {


        RKM.setName("Runge-Kutta Method");

        arrayListX.add(initialX);
        arrayListY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrayListX.add(myRKM_X(h, arrayListX.get(i - 1)));
        }

        for (int i = 1; i < m + 1; i++) {
            arrayListY.add(myRKM_Y(h, arrayListX.get(i - 1), arrayListY.get(i - 1)));
        }


        writer.write("RKM_X: " + arrayListX.toString() + "\n");
        writer.write("RKM_Y: " + arrayListY.toString() + "\n");

        RKM.setData(plot(arrayListX, arrayListY));

    }

    public static double myEulerX(double h, double xPre) {

        return xPre + h;
    }

    public static double myEulerY(double h, double xPre, double yPre) {

        return yPre + h * (myFunction(xPre, yPre));
    }

    public static double myIEulerX(double h, double xPre) {

        return xPre + h;
    }

    public static double myIEulerY(double h, double xPre, double yPre) {

        return yPre + h * (myFunction(xPre + h / 2, yPre + h / 2 * myFunction(xPre, yPre)));
    }

    public static double myRKM_X(double h, double xPre) {

        return xPre + h;
    }

    public static double myRKM_Y(double h, double xPre, double yPre) {
        double k1 = myFunction(xPre, yPre);
        double k2 = myFunction(xPre + h / 2, yPre + h * k1 / 2);
        double k3 = myFunction(xPre + h / 2, yPre + h * k2 / 2);
        double k4 = myFunction(xPre + h, yPre + h * k3);

        return yPre + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }

    //change this function to others
    public static double myFunction(double x, double y) {

        return 4 / (x * x) - y / x - y * y;
    }

    public static ObservableList<XYChart.Data<Double, Double>> plot(ArrayList<Double> arrListX, ArrayList<Double> arrListY) {
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
