import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;

class Model {
    void plotEM(double x0, double y0, double X, double step, Logs logs) throws IOException {
        //separation times
        int m = (int) ((X - x0) / step);
        //use Euler's method
        EulerMethod EM = new EulerMethod(step, m, x0, y0, logs);
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

    void plotImEM(double x0, double y0, double X, double step, Logs logs) throws IOException {
        //separation times
        int m = (int) ((X - x0) / step);
        //use Improved Euler's method
        ImprovedEulerMethod ImEM = new ImprovedEulerMethod(step, m, x0, y0, logs);
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

    void plotRKM(double x0, double y0, double X, double step, Logs logs) throws IOException {
        //separation times
        int m = (int) ((X - x0) / step);
        //use Runge-Kutta's method
        RKMethod RKM = new RKMethod(step, m, x0, y0, logs);
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

    void plotAll(double x0, double y0, double X, double step, Logs logs) throws IOException {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<>(x, y);


        LineChartHover lineChartHover = new LineChartHover();

        numberLineChart.setCursor(Cursor.CROSSHAIR);
        numberLineChart.setTitle("Differential equations");

        int m = (int) ((X - x0) / step);

        XYChart.Series EM = new XYChart.Series();
        EM.setName("Euler's Method");
        EulerMethod eulerMethod = new EulerMethod(step, m, x0, y0, logs);
        EM.setData(lineChartHover.plot(eulerMethod.getArrX(), eulerMethod.getArrY()));
        numberLineChart.getData().add(EM);

        XYChart.Series IEM = new XYChart.Series();
        IEM.setName("Improved Euler's Method");
        ImprovedEulerMethod imEulerMethod = new ImprovedEulerMethod(step, m, x0, y0, logs);
        EM.setData(lineChartHover.plot(imEulerMethod.getArrX(), imEulerMethod.getArrY()));
        numberLineChart.getData().add(IEM);

        XYChart.Series RKM = new XYChart.Series();
        RKM.setName("Runga-Kutta's Method");
        RKMethod rkMethod = new RKMethod(step, m, x0, y0, logs);
        RKM.setData(lineChartHover.plot(rkMethod.getArrX(), rkMethod.getArrY()));
        numberLineChart.getData().add(RKM);

        XYChart.Series exact = new XYChart.Series();
        exact.setName("Exact Solution");
        Exact exactSolution = new Exact(step, m, x0, y0, logs);
        exact.setData(lineChartHover.plot(exactSolution.getArrX(), exactSolution.getArrY()));
        numberLineChart.getData().add(exact);

        Scene scene = new Scene(numberLineChart);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();

        plotErrors(exactSolution, lineChartHover, eulerMethod, imEulerMethod, rkMethod, logs);
    }

    private void plotErrors(Exact exact, LineChartHover lineChartHover, EulerMethod EM, ImprovedEulerMethod IEM, RKMethod RKM, Logs logs) throws IOException {

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(x, y);

        lineChart.setTitle("Errors");

        XYChart.Series errorSeriesEM = new XYChart.Series();
        errorSeriesEM.setName("Errors for EM");
        Error errorEM = new Error("Euler method", exact.getArrX(), exact.getArrY(), EM.getArrX(), EM.getArrY(), logs);
        errorSeriesEM.setData(lineChartHover.plot(errorEM.getErrorArrX(), errorEM.getErrorArrY()));
        lineChart.getData().add(errorSeriesEM);

        XYChart.Series errorSeriesIEM = new XYChart.Series();
        errorSeriesIEM.setName("Errors for IEM");
        Error errorIEM = new Error("Improved Euler method", exact.getArrX(), exact.getArrY(), IEM.getArrX(), IEM.getArrY(), logs);
        errorSeriesIEM.setData(lineChartHover.plot(errorIEM.getErrorArrX(), errorIEM.getErrorArrY()));
        lineChart.getData().add(errorSeriesIEM);

        XYChart.Series errorSeriesRKM = new XYChart.Series();
        errorSeriesRKM.setName("Errors for RKM");
        Error errorRKM = new Error("Runga-Kutta's method", exact.getArrX(), exact.getArrY(), RKM.getArrX(), RKM.getArrY(), logs);
        errorSeriesRKM.setData(lineChartHover.plot(errorRKM.getErrorArrX(), errorRKM.getErrorArrY()));
        lineChart.getData().add(errorSeriesRKM);

        lineChart.setCursor(Cursor.CROSSHAIR);

        Scene scene = new Scene(lineChart);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }
}
