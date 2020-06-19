package project1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkCostFinder {
    int k;
    int[][] capacity;
    int[][] costMatrix;
    int[][] demandMatrix;
    long cost;
    ArrayList<Integer> path;
    NetworkCostFinder(int k){
        this.k=k;
        this.capacity = new int[25][25];
        this.cost=0;
    }

    public void findMinPath(){
        GenerateCost generateCost = new GenerateCost();
        GenerateDemand demand = new GenerateDemand();
        costMatrix = generateCost.getCostMatrix(k);
        demandMatrix = demand.getDemandMatrix();

        for(int i=0;i<25;i++){
            dijkstra(i);
        }

    }
    private void dijkstra(int src){
        int[] dist = new int[25];
        boolean[] visited = new boolean[25];
        int[] parent = new int[25];
        
        for(int i=0;i<25;i++){
            parent[i] = -1;
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        dist[src] = 0;

        for(int i=0;i<24;i++){
            int u = minDistance(dist,visited);
            visited[u] = true;
            for (int v = 0; v < 25; v++) {

                if (!visited[v] && costMatrix[u][v] > 0 && (dist[u] + costMatrix[u][v]) < dist[v])
                {
                    parent[v]  = u;
                    dist[v] = dist[u] + costMatrix[u][v];
                }
            }
          updateCostAndCapacity(dist,parent,src);
        }
        }

    private void updateCostAndCapacity(int[] dist, int[] parent, int src) {
        for(int i=0;i<25;i++){
            if(demandMatrix[src][i]>0){
               path = new ArrayList<>();
                getPath(i,parent);
                if(path.size()>0){
                    int sum=0;
                    int demand = demandMatrix[src][i];
                    for(int index=0;index<path.size()-1;index++){
                        capacity[path.get(index)][path.get(index+1)] += demand;
                        sum+=costMatrix[path.get(index)][path.get(index+1)];
                    }
                    cost+=(sum*demand);
                }
            }
        }
    }

    private void getPath(int currentVertex, int[] parent) {
        if(currentVertex==-1){
            return;
        }
        getPath(parent[currentVertex],parent);
        path.add(currentVertex);
    }


    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int min_index=-1;
        for(int v=0;v<25;v++){
            if(visited[v]==false && dist[v]<=min){
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    public static void main(String[] arg) throws IOException {
        int k=3;
        for(k=3;k<=13;k++){
            NetworkCostFinder networkCostFinder = new NetworkCostFinder(k);
            networkCostFinder.findMinPath();
            System.out.println("K-Value:"+k);
            System.out.println("Cost of network is "+networkCostFinder.cost);
            BufferedWriter fw = new BufferedWriter(new FileWriter("/Volumes/Macintosh_HD/ATN/k"+k+"_graph.csv"));
            long edgeCount=0;
            for(int i=0;i<25;i++){
                //System.out.println();
                for(int j=0;j<25;j++){
                    //System.out.print(networkCostFinder.capacity[i][j]+" ");
                    if(i!=j && networkCostFinder.capacity[i][j]>0){
                          fw.write(i+","+j+ ","+ networkCostFinder.capacity[i][j]);
                          fw.newLine();
                        edgeCount++;
                    }

                }
            }
            fw.close();
            System.out.println("Number of edges:"+edgeCount);
            System.out.println("Density of the Network "+ (edgeCount/(25.0*24.0)));
            System.out.println();
        }

    }
}
