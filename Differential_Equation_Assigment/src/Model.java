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
        System.out.println(EM.getArrX().size());
        LineChartHover lineChartHover = new LineChartHover(EM.getArrX(), EM.getArrY(), "Euler Method");
        lineChartHover.getLineChart().setCursor(Cursor.CROSSHAIR);
        lineChartHover.getLineChart().setTitle("Differential equations");
        //generate Scene for EM
        Scene scene = new Scene(lineChartHover.getLineChart());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setFullScreen(false);
        newStage.show();
    }
}
