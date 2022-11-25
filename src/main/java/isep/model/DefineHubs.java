package isep.model;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import isep.utils.MergeSort;


public class DefineHubs {
    // Number of Hubs to find
    int N;
    DistributionNetwork distN;

    public DefineHubs(int n, DistributionNetwork dn){
        if(n <= 0) throw(new NumberFormatException("N cannot be lower than 1!"));
        if(dn == null) throw(new NullPointerException("Distribution Network cannot be null!"));

        this.N = n;
        this.distN = dn;
    }

    public List<Enterprise> execute(){

        List<Map.Entry<Enterprise, Double>> list = new ArrayList<>();
        
        // if the graph is not connect there is no hub
        // if(!distN.isConnected) return distN;

        List<Enterprise> enterprises = distN.getEnterprises();
        List<Entity> nonEnterprises = distN.getNonEnterprises();

        for (int i = 0; i < enterprises.size(); i++) {

            int count = 0;
            double sum = 0;
            Enterprise e1 = enterprises.get(i);

            // if e1 was a Hub before unMakes it
            e1.unMakeHub();
            
            // sums the shortest path size to from e1 to all nonEnterprises
            // count paths between e1 and all nonEnterprises
            for (int j = 0; j < nonEnterprises.size(); j++) {
                sum += distN.shortestPathSize(e1, nonEnterprises.get(j));
                count++;
            }

            // calc avg
            list.add(new AbstractMap.SimpleEntry<Enterprise, Double>(e1, sum/(double)count));
        }

        // order list
        list = new MergeSort<Map.Entry<Enterprise, Double>>().sort(list, cmp);

        // make N enterprises Hubs and return them in a list
        List<Enterprise> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Enterprise hub = list.get(i).getKey();
            hub.makeHub();
            result.add(hub);
        } 
        return result;
    }

    private final Comparator<Map.Entry<Enterprise, Double>> cmp = new Comparator<Map.Entry<Enterprise, Double>>(){
        @Override
        public int compare(Map.Entry<Enterprise, Double> o1, Map.Entry<Enterprise, Double> o2) {
          return (int) (o1.getValue()-o2.getValue());
        }
    };

}