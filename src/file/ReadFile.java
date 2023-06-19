package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logic.CalcUtils;
import model.CVRPInstance;

public class ReadFile {
    private FileReader archive;
    private boolean readSuccess;

    public ReadFile(String path) {
        try {
            archive = new FileReader(path);
            readSuccess = true;
        } catch (FileNotFoundException e) {
            readSuccess = false;
            System.err.println(e.getMessage() + ": " + path);
        }
    }

    public boolean getReadSuccess() { return readSuccess; }

    public CVRPInstance createInstance() throws IOException {
        BufferedReader buffer = new BufferedReader(archive);
        
        String[] info = buffer.lines().toArray(String[]::new);
        buffer.close();

        String instanceName = info[0].substring(7);
        int dimension = Integer.parseInt(info[3].substring(12));
        int capacity = Integer.parseInt(info[5].substring(11));
        int vehicles = Integer.parseInt(info[1].substring(info[1].indexOf("No of trucks: ")+14, info[1].lastIndexOf(", ")));
        CVRPInstance instance = new CVRPInstance(instanceName, dimension);
        instance.setCapacity(capacity);
        instance.setVehicles(vehicles);

        List<String> coordinates;
        List<Integer> coordX = new ArrayList<>();
        List<Integer> coordY = new ArrayList<>();
        for(int i = 0; i < dimension; i++) {
            coordinates = Arrays.asList(info[i+7].split(" "));
            coordX.add(Integer.parseInt(coordinates.get(1)));
            coordY.add(Integer.parseInt(coordinates.get(2)));
        }

        CalcUtils calc = new CalcUtils();
        int[][] distances = calc.Euclidian2DtoDistanceMatrix(coordX, coordY);
        instance.setDistMatrix(distances);

        int[] demands = new int[dimension];
        for (int i = 0; i < demands.length; i++) {
            demands[i] = Integer.parseInt(info[i+dimension+8]
            .substring(info[i+dimension+8].indexOf(" ")+1, info[i+dimension+8].lastIndexOf(" ")));
        }
        instance.setDemand(demands);

        return instance;
    }
}
