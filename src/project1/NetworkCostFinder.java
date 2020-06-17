package project1;

import java.util.List;

public class NetworkCostFinder {
    int k;
    int[][] capacity;
    int[][] costMatrix;
    int[][] demandMatrix;
    long cost;
    NetworkCostFinder(int k){
        this.k=k;
        this.capacity = new int[25][25];
        this.cost=0;
    }

    public void findMinPath(int k){
        GenerateCost generateCost = new GenerateCost();
        GenerateDemand demand = new GenerateDemand();
        costMatrix = generateCost.getCostMatrix(k);
        demandMatrix = demand.getDemandMatrix();

        for(int i=0;i<demandMatrix.length;i++){
            for(int j=0;j<demandMatrix.length;j++){
                if(demandMatrix[i][j] > 0){
                   //find best path
                }
            }
        }

    }
    public static void main(String[] arg){
        int k=3;

    }
}
