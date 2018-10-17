import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Model {

    public static ArrayList<Double> arrayListX = new ArrayList<>();
    public static ArrayList<Double> arrayListY = new ArrayList<>();

    public static XYChart.Series EM = new XYChart.Series();

    public static FileWriter writer;

    public void plot(double x0, double y0, double X, double step) throws IOException {

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(x, y);

        lineChart.setTitle("Integrable curve");

        int m = (int) ((X - x0) / step); //separation times

        writer = new FileWriter("output.txt");

        EM(step, m, x0, y0);
        lineChart.getData().add(EM);
        arrayListX.clear();
        arrayListY.clear();
        writer.write("---------------------------------------------------------------------------------------------------\n");

        Scene scene = new Scene(lineChart, 600, 600);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();

        writer.flush();
        writer.close();
    }

    public static void EM(double step, int m, double initialX, double initialY) throws IOException {


        EM.setName("Euler Method");

        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();


        arrayListX.add(initialX);
        arrayListY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrayListX.add(myEulerX(step, arrayListX.get(i - 1)));
        }

        for (int i = 1; i < m + 1; i++) {
            arrayListY.add(myEulerY(step, arrayListX.get(i - 1), arrayListY.get(i - 1)));
        }


        writer.write("EM_X: " + arrayListX.toString() + "\n");
        writer.write("EM_Y: " + arrayListY.toString() + "\n");

        for (int i = 0; i < m; i++) {
            data.add(new XYChart.Data(arrayListX.get(i), arrayListY.get(i)));
        }

        EM.setData(data);

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
