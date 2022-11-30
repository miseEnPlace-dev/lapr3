package isep.model;

import java.util.ArrayList;
import java.util.List;

import isep.utils.graph.AdjacencyMapGraph;

public class NetworkGraph<V, E> extends AdjacencyMapGraph<V, E>{

    public NetworkGraph(boolean directed) {
        super(directed);
    }

    public <T extends Entity> List<T> getEntitiesWithClass(Class c){
        List<T> result = new ArrayList<>();
        for (V vertice : super.mapVertices.keySet()) {
            if(vertice.getClass() == c)
                result.add((T) vertice);
        }
        return result;
    }
    
}
