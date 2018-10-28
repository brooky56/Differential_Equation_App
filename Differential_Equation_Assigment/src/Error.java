import java.io.IOException;
import java.util.ArrayList;

class Error {
    private ArrayList<Double> errorArrX = new ArrayList<>();
    private ArrayList<Double> errorArrY = new ArrayList<>();

    Error(String methodName, ArrayList<Double> exactArrX, ArrayList<Double> exactArrY, ArrayList<Double> arrX, ArrayList<Double> arrY, Logs logs) throws IOException {
        computeError(exactArrX, exactArrY, arrX, arrY);
        saveLogs(logs, methodName);
    }

    public ArrayList<Double> getErrorArrX() {
        return errorArrX;
    }

    public ArrayList<Double> getErrorArrY() {
        return errorArrY;
    }

    private void computeError(ArrayList<Double> exactArrX, ArrayList<Double> exactArrY, ArrayList<Double> arrX, ArrayList<Double> arrY) {
        for (int i = 0; i < arrX.size(); i++) {
            errorArrX.add(exactArrX.get(i));
        }
        for (int i = 0; i < arrY.size(); i++) {
            errorArrY.add(exactArrY.get(i) - arrY.get(i));
        }
    }

    private void saveLogs(Logs logs, String methodName) throws IOException {
        logs.writer.write("Error for " + methodName + " " + errorArrX.toString() + "\n");
        logs.writer.write("Error for " + methodName + " " + errorArrY.toString() + "\n");
        logs.writer.write("---------------------------------------------------------------------------------------------------\n");
    }
}
