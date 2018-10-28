import java.io.IOException;
import java.util.ArrayList;

class Exact {

    private ArrayList<Double> arrX = new ArrayList<>();
    private ArrayList<Double> arrY = new ArrayList<>();

    public ArrayList<Double> getArrX() {
        return arrX;
    }

    public ArrayList<Double> getArrY() {
        return arrY;
    }

    Exact(double step, int m, double x0, double y0, Logs logs) throws IOException {
        exact(step, m, x0, y0);
        saveLogs(logs);
    }

    private void exact(double step, int m, double initialX, double initialY) throws IOException {
        arrX.add(initialX);
        arrY.add(initialY);
        System.out.println(m);
        for (int i = 1; i < m + 1; i++) {
            arrX.add(arrX.get(i - 1) + step);
        }
        for (int i = 1; i < m + 1; i++) {
            arrY.add(realFunc(arrX.get(i)));
        }
    }

    private void saveLogs(Logs logs) throws IOException {
        logs.writer.write("Exact_X: " + arrX.toString() + "\n");
        logs.writer.write("Exact_Y: " + arrY.toString() + "\n");
        logs.writer.write("---------------------------------------------------------------------------------------------------\n");
    }

    private Double realFunc(double x) {

        return (0.4 + 2 * x * x * x * x) / (x * (-0.2 + x * x * x * x));
    }
}
