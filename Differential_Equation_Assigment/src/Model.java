import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

class Model {

    void plotEM(double x0, double y0, double X, double step) throws IOException {
        
        int m = (int) ((X - x0) / step); //separation times


        EulerMethod EM = new EulerMethod(step, m, x0, y0);

        FileWriter writer = new FileWriter("output.txt");

        LineChartHover lineChartHover = new LineChartHover(EM.getArrX(), EM.getArrY(), "Euler Method");


        writer.write("EM_X: " + EM.getArrX().toString() + "\n");
        writer.write("EM_Y: " + EM.getArrY().toString() + "\n");

        EM.getArrX().clear();
        EM.getArrY().clear();
        writer.write("---------------------------------------------------------------------------------------------------\n");

        writer.flush();
        writer.close();

        lineChartHover.getLineChart().setCursor(Cursor.CROSSHAIR);
        lineChartHover.getLineChart().setTitle("Differential equations");

        Scene scene = new Scene(lineChartHover.getLineChart());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setFullScreen(true);
        newStage.show();
    }
}
