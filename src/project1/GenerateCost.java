package project1;

import java.util.ArrayList;
import java.util.Collections;

public class GenerateCost {
    int[][] cost = new int[25][25];
    ArrayList<Integer> list;

    public int[][] getCostMatrix(int k){
        for(int i=0;i<25;i++){
            for(int j=0;j<25;j++){
                if(i==j)
                    cost[i][j]=0;
                else
                    cost[i][j] = 250;
            }
        }

        for(int i=0;i<25;i++){
                    int index=0;
                    int count=0;
                    getRandomNodes();
                    while (count<k){
                        int nodeJ = list.get(index);
                        if(i==nodeJ){
                            index++;
                        } else {
                            cost[i][nodeJ] = 1;
                            index++;
                            count++;
                        }
                    }
        }
       return cost;
    }

    public void getRandomNodes(){
        list = new ArrayList<>();
        for (int i=0; i<=24; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

    }

}
