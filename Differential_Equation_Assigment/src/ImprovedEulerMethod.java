import java.io.IOException;
import java.util.ArrayList;

class ImprovedEulerMethod {
    private ArrayList<Double> arrX = new ArrayList<>();
    private ArrayList<Double> arrY = new ArrayList<>();

    ImprovedEulerMethod(double step, int m, double x0, double y0) throws IOException {
        ImEM(step, m, x0, y0);
        saveLogs();
    }

    public ArrayList<Double> getArrX() {
        return arrX;
    }

    public ArrayList<Double> getArrY() {
        return arrY;
    }

    private void ImEM(double step, int m, double initialX, double initialY) throws IOException {

        arrX.add(initialX);
        arrY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrX.add((myImEulerX(step, arrX.get(i - 1))));
        }

        for (int i = 1; i < m + 1; i++) {
            arrY.add(myImEulerY(step, arrX.get(i - 1), arrY.get(i - 1)));
        }
    }

    private void saveLogs() throws IOException {
        Logs logs = new Logs();
        logs.writer.write("ImEM_X: " + arrX.toString() + "\n");
        logs.writer.write("ImEM_Y: " + arrY.toString() + "\n");
        logs.writer.write("---------------------------------------------------------------------------------------------------\n");
    }

    private static double myImEulerX(double h, double xPre) {

        return xPre + h;
    }

    private static double myImEulerY(double h, double xPre, double yPre) {

        return yPre + h * (myFunction(xPre + h / 2, yPre + h / 2 * myFunction(xPre, yPre)));
    }

    private static double myFunction(double x, double y) {

        return 4 / (x * x) - y / x - y * y;
    }
}
