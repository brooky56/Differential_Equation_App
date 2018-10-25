import java.io.IOException;
import java.util.ArrayList;

class RKMethod {
    private ArrayList<Double> arrX = new ArrayList<>();
    private ArrayList<Double> arrY = new ArrayList<>();

    RKMethod(double step, int m, double x0, double y0) throws IOException {
        RKM(step, m, x0, y0);
        saveLogs();
    }

    public ArrayList<Double> getArrX() {
        return arrX;
    }

    public ArrayList<Double> getArrY() {
        return arrY;
    }

    private void RKM(double step, int m, double initialX, double initialY) throws IOException {

        arrX.add(initialX);
        arrY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrX.add((myRKmX(step, arrX.get(i - 1))));
        }

        for (int i = 1; i < m + 1; i++) {
            arrY.add(myRKmY(step, arrX.get(i - 1), arrY.get(i - 1)));
        }
    }

    private void saveLogs() throws IOException {
        Logs logs = new Logs();
        logs.writer.write("RKM_X: " + arrX.toString() + "\n");
        logs.writer.write("RKM_Y: " + arrY.toString() + "\n");
        logs.writer.write("---------------------------------------------------------------------------------------------------\n");
    }

    private static double myRKmX(double h, double xPre) {

        return xPre + h;
    }

    private static double myRKmY(double h, double xPre, double yPre) {

        double k1 = myFunction(xPre, yPre);
        double k2 = myFunction(xPre + h / 2, yPre + h * k1 / 2);
        double k3 = myFunction(xPre + h / 2, yPre + h * k2 / 2);
        double k4 = myFunction(xPre + h, yPre + h * k3);

        return yPre + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }

    private static double myFunction(double x, double y) {

        return 4 / (x * x) - y / x - y * y;
    }
}