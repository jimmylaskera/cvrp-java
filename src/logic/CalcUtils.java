package logic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalcUtils {
    public int[][] Euclidian2DtoDistanceMatrix(List<Integer> coordX, List<Integer> coordY) {
        int[] cx = coordX.stream().mapToInt(Integer::intValue).toArray();
        int[] cy = coordY.stream().mapToInt(Integer::intValue).toArray();

        int[][] distances = new int[coordX.size()][coordY.size()];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances.length; j++) {
                double x = Math.pow(Double.valueOf(cx[i]-cx[j]), 2.0);
                double y = Math.pow(Double.valueOf(cy[i]-cy[j]), 2.0);
                distances[i][j] = (i==j) ? Integer.MAX_VALUE : (int) Math.sqrt(x + y);
            }
        }
        return distances;
    }

    public boolean isThereAnyDemand(int[] demand) {
        for (int i: demand) if (i > 0) return true;
        return false;
    }

    public int getClosestToDepot(int[][] distances) {
        List<Integer> dist0 = Arrays.stream(distances[0]).boxed().toList();
        return dist0.indexOf(Collections.min(dist0));
    }
}
