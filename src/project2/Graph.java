package project2;

import java.util.HashMap;
import java.util.Map;

public class Graph {

    public static int[] studentID = new int[]{2,0,2,1,4,8,6,1,1,5};
    //Data structures to store edge and node information
    public int adjMatrix[][];
    public int edgeToNodesMap[][];
    Map<String,Integer> nodesToEdgeMap;
    //Probability to generate link reliability values
    public double p;

    public Graph(){
        p = 0;
        adjMatrix = new int[5][5];
        edgeToNodesMap = new int[10][2];
        nodesToEdgeMap = new HashMap<>();
        int key = 0;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if (i != j) {
                    if(i > j) {
                        edgeToNodesMap[key][0]= i;
                        edgeToNodesMap[key][1]= j;
                        String str = ""+i+","+j;
                        nodesToEdgeMap.put(str,key);
                        key++;
                    }
                    adjMatrix[i][j]=1;
                }
                else
                    adjMatrix[i][j]=0;
            }
        }
    }

    /**
     * Depth first search algorithm. Helper function to check the graph connectivity
     * @param i
     * @param visited
     */
    public void dfs(int i, boolean visited[]) {

        visited[i] = true;
        for(int j = 0;j < 5; j++){
            if(adjMatrix[i][j] == 1){
                if(!visited[j]){
                    dfs(j, visited);
                }
            }
        }

    }

    /**
     * Check the graph connectivity to decide whether the network state is UP or DOWN
     * @return
     */
    public boolean isGraphConnected(){
        boolean seen[] = new boolean[5];
        boolean isConnected = true;
        for(int i = 0; i < 5; i++){
            seen[i] = false;
        }

        dfs(0, seen);

        for(int i = 0;i < 5; i++){
            if(!seen[i]){
                isConnected = false;
            }
        }
        return isConnected;
    }

    /**
     * Calculates the reliability of the network topology by using individual link reliability
     * @return
     */
    public double calculateReliability() {
        double reliability = 1;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if (i > j){

                    //if the link is up
                    if (adjMatrix[i][j] == 1) {
                        reliability = reliability * getReliability(i,j, p);
                    }
                    //if the link is down
                    else {
                        reliability = reliability * (1 - getReliability(i,j, p));
                    }

                }
            }
        }
        return reliability;
    }

    /**
     * Generates the link reliability based on the student ID
     * @param i indicates i'th node
     * @param j indicates j'th node
     * @param p given probability
     * @return
     */
    public double getReliability(int i,int j, double p){
        String str = ""+i+","+j;
        int value = (int) Math.ceil(studentID[nodesToEdgeMap.get(str)]/3.0);
        return Math.pow(p,value);
    }



}
