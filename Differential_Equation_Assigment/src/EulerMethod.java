import java.io.IOException;
import java.util.ArrayList;

class EulerMethod {

    private ArrayList<Double> arrX = new ArrayList<>();
    private ArrayList<Double> arrY = new ArrayList<>();

    EulerMethod(double step, int m, double x0, double y0) throws IOException {
        EM(step, m, x0, y0);
    }

    public ArrayList<Double> getArrX() {
        return arrX;
    }

    public ArrayList<Double> getArrY() {
        return arrY;
    }

    private void EM(double step, int m, double initialX, double initialY) throws IOException {

        arrX.add(initialX);
        arrY.add(initialY);

        for (int i = 1; i < m + 1; i++) {
            arrX.add((myEulerX(step, arrX.get(i - 1))));
        }

        for (int i = 1; i < m + 1; i++) {
            arrY.add(myEulerY(step, arrX.get(i - 1), arrY.get(i - 1)));
        }
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
