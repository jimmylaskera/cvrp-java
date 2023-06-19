package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import logic.NearestNeighbor;

public class PrintToFile {
    private FileWriter archive;

    public PrintToFile(String path) {
        File outFolder = new File("./src/output/");
        if (!outFolder.exists()) outFolder.mkdir();

        try {
            archive = new FileWriter(outFolder.toString() + "/" + path, Charset.forName("utf-8"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveResults(NearestNeighbor nn, long time) {
        try {
            archive.write("Instância: " + nn.getInstance().getName() + "\n");
            archive.write("Peso total: " + nn.getWeight() + "\n");
            archive.write("Tempo de execução: " + time + " nanosegundos.\n\n");
            
            HashMap<Integer, List<Integer>> routes = nn.getRoutes();
            for (Integer i : routes.keySet()) {
                archive.write("Rota " + (i+1) + ": ");
                for (Integer j: routes.get(i)) archive.write(j + " ");
                archive.write("\n");
            }

            archive.flush();
            archive.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
