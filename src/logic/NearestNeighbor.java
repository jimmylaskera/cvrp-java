package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.CVRPInstance;

public class NearestNeighbor {
    private CVRPInstance instance;
    private HashMap<Integer, List<Integer>> routes;
    private int[][] distances;
    private int[] demand;

    public NearestNeighbor(CVRPInstance instance) {
        this.instance = instance;
        this.routes = new HashMap<>(instance.getVehicles());
        distances = new int[instance.getDimension()][instance.getDimension()];
        demand = new int[instance.getDimension()];
        for (int i = 0; i < instance.getDimension(); i++) {
            for (int  j = 0; j < instance.getDimension(); j++) distances[i][j] = instance.getDistMatrix()[i][j];
        }
        for (int i = 0; i < instance.getDimension(); i++) demand[i] = instance.getDemand()[i];
    }

    public void findRoutes() {
        // Inicialização das estruturas que serão utilizadas. São feitas uma cópia da
        // matriz distância e do vetor demanda, para que os dados originais não sejam
        // afetados pela execução da função.
        List<Integer> vpos = new ArrayList<>();
        List<Integer> vcap = new ArrayList<>();
        List<Integer> vdist = new ArrayList<>();
        for (int i = 0; i < instance.getVehicles(); i++) {
            vpos.add(0);
            vcap.add(instance.getCapacity());
            routes.put(i, new ArrayList<>());
        }

        CalcUtils calc = new CalcUtils();

        // Enquanto houver valores acima de zero no vetor demanda, o algoritmo continua.
        // Caso todos os veículos iniciais estiverem cheios, uma nova rota é criada e o
        // algoritmo continua até que todas as demandas estejam supridas.
        while(calc.isThereAnyDemand(demand)) {
            // O próximo cliente é decidido, pela menor distância ao ponto central (depósito).
            int client = calc.getClosestToDepot(distances);
            
            // O veículo a atender tal cliente é decidido pela menor distância entre sua
            // localização atual e a do cliente.
            for (int i: vpos) vdist.add(distances[i][client]);
            int k = vdist.indexOf(Collections.min(vdist));

            // Este loop é usado para o caso de que seja escolhido um novo veículo seja escolhido
            // caso ele não tenha capacidade para atender este cliente, saindo apenas quando um
            // veículo tenha sido capaz de atender o cliente.
            while(true) {
                if (demand[client] <= vcap.get(k)) {
                    List<Integer> vroute = routes.get(k);

                    vroute.add(client);
                    routes.put(k, vroute);

                    vcap.set(k, vcap.get(k)-demand[client]);
                    vdist.clear();
                    distances[vpos.get(k)][client] = Integer.MAX_VALUE;
                    distances[client][vpos.get(k)] = Integer.MAX_VALUE;
                    distances[0][client] = Integer.MAX_VALUE;

                    vpos.set(k, client);
                    demand[client] = 0;
                    break;
                } else {
                    vdist.set(k, Integer.MAX_VALUE);
                    k = vdist.indexOf(Collections.min(vdist));

                    // Verifica se o último índice escolhido nos informa que não há mais
                    // veículos com capacidade para atender o próximo cliente. Então uma nova
                    // rota é criada e adicionada ao conjunto, permitindo que o algoritmo
                    // continue até atender todos os clientes.
                    if (vdist.get(k) == Integer.MAX_VALUE) {
                        
                        List<Integer> vroute = new ArrayList<>();
                        vroute.add(client);
                        routes.put(routes.size(), vroute);

                        vcap.add(vcap.size(), instance.getCapacity()-demand[client]);

                        vdist.clear();
                        distances[0][client] = Integer.MAX_VALUE;
                        distances[client][0] = Integer.MAX_VALUE;
                        vpos.add(vpos.size(), client);

                        demand[client] = 0;
                        break;
                    }
                }
            }
        }
    }

    public CVRPInstance getInstance() {
        return instance;
    }

    public HashMap<Integer, List<Integer>> getRoutes() {
        return routes;
    }

    public int getWeight() {
        int weight = 0;
        int origin = 0, destiny = 0;

        for (int i: routes.keySet()) {
            origin = 0;
            destiny = routes.get(i).get(0);
            weight += instance.getDistMatrix()[0][destiny];

            for (int j = 1; j < routes.get(i).size(); j++) {
                origin = destiny;
                destiny = routes.get(i).get(j);
                weight += instance.getDistMatrix()[origin][destiny];
            }

            weight += instance.getDistMatrix()[destiny][0];
        }
        return weight;
    }
}
