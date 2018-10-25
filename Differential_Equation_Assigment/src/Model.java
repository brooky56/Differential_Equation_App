import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

class Model {
    void plotEM(double x0, double y0, double X, double step) throws IOException {
        //separation times
        int m = (int) ((X - x0) / step);
        //use Euler's method
        EulerMethod EM = new EulerMethod(step, m, x0, y0);
        //plot lineChart
        LineChartHover lineChartHover = new LineChartHover(EM.getArrX(), EM.getArrY(), "Euler's Method");
        lineChartHover.getLineChart().setCursor(Cursor.CROSSHAIR);
        lineChartHover.getLineChart().setTitle("Differential equations");
        //generate Scene for EM
        Scene scene = new Scene(lineChartHover.getLineChart());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    void plotImEM(double x0, double y0, double X, double step) throws IOException {
        //separation times
        int m = (int) ((X - x0) / step);
        //use Improved Euler's method
        ImprovedEulerMethod ImEM = new ImprovedEulerMethod(step, m, x0, y0);
        //plot lineChart
        LineChartHover lineChartHover = new LineChartHover(ImEM.getArrX(), ImEM.getArrY(), "Improved Euler's Method");
        lineChartHover.getLineChart().setCursor(Cursor.CROSSHAIR);
        lineChartHover.getLineChart().setTitle("Differential equations");
        //generate Scene for ImEM
        Scene scene = new Scene(lineChartHover.getLineChart());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    void plotRKM(double x0, double y0, double X, double step) throws IOException {
        //separation times
        int m = (int) ((X - x0) / step);
        //use Runge-Kutta's method
        RKMethod RKM = new RKMethod(step, m, x0, y0);
        //plot lineChart
        LineChartHover lineChartHover = new LineChartHover(RKM.getArrX(), RKM.getArrY(), "Runge-Kutta's Method");
        lineChartHover.getLineChart().setCursor(Cursor.CROSSHAIR);
        lineChartHover.getLineChart().setTitle("Differential equations");
        //generate Scene for RKM
        Scene scene = new Scene(lineChartHover.getLineChart());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }
}
