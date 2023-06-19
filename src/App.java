import java.io.File;
import java.util.HashMap;
import java.util.List;

import file.PrintToFile;
import file.ReadFile;
import logic.NearestNeighbor;
import model.CVRPInstance;

public class App {
    public static void main(String[] args) throws Exception {
        switch(args.length) {
            case 0:
                System.out.println("Imprimindo todos os testes:\n");
                File path = new File("./src/tests/");
                String[] tests = path.list();
                
                for (String test : tests) {
                    ReadFile file = new ReadFile("./src/tests/" + test);

                    if (file.getReadSuccess()) {
                        CVRPInstance instance = file.createInstance();
                        NearestNeighbor cvrp = new NearestNeighbor(instance);
                        
                        long startTime = System.nanoTime();
                        cvrp.findRoutes();
                        long finishTime = System.nanoTime();

                        System.out.println(instance.getName());
                        System.out.println("Processo concluído. Tempo: " + (finishTime-startTime) + " ns.");
                        System.out.println("Peso total: " + cvrp.getWeight());
                        
                        HashMap<Integer, List<Integer>> routes = cvrp.getRoutes();
                        System.out.println("Rotas encontradas:");
                        for (Integer i : routes.keySet()) {
                            System.out.printf("Rota " + (i+1) + ": ");
                            for (Integer j: routes.get(i)) System.out.printf(j + " ");
                            System.out.println();
                        }
                        System.out.println();
                        
                        PrintToFile output = new PrintToFile("Resultado " + instance.getName() + ".txt");
                        output.saveResults(cvrp, finishTime-startTime);
                    } else System.out.println("Wrong file path, try again.");
                }

                System.out.println("Encontrados " + tests.length + " testes.");
                break;

            case 1:
                ReadFile file = new ReadFile("./src/tests/" + args[0]);

                if (file.getReadSuccess()) {
                    CVRPInstance instance = file.createInstance();
                    NearestNeighbor cvrp = new NearestNeighbor(instance);
                    
                    long startTime = System.nanoTime();
                    cvrp.findRoutes();
                    long finishTime = System.nanoTime();

                    System.out.println(instance.getName());
                    System.out.println("Processo concluído. Tempo: " + (finishTime-startTime) + " ns.");
                    System.out.println("Peso total: " + cvrp.getWeight());
                    
                    HashMap<Integer, List<Integer>> routes = cvrp.getRoutes();
                    System.out.println("Rotas encontradas:");
                    for (Integer i : routes.keySet()) {
                        System.out.printf("Rota " + (i+1) + ": ");
                        for (Integer j: routes.get(i)) System.out.printf(j + " ");
                        System.out.println();
                    }

                    PrintToFile output = new PrintToFile("Resultado " + instance.getName() + ".txt");
                    output.saveResults(cvrp, finishTime-startTime);
                } else System.out.println("Arquivo não encontrado, tente novamente.");
                break;

            default:
                System.out.println("Argumento incorreto. Leia o README para mais informações.");
                break;
        }
    }
}
