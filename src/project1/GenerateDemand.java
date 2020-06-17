package project1;

public class GenerateDemand {


    public int[][] getDemandMatrix(){
        int[][] demand = new int[25][25];
        String sID = "2021486115";
        int[] generatedID = new int[]{2,0,2,1,4,8,6,1,1,5,2,0,2,1,4,8,6,1,1,5,2,0,2,1,4};
        for(int i=0;i<=24;i++){
           for(int j=0;j<=24;j++){
               if(i==j){
                   demand[i][j] = 0;
               } else{
                   demand[i][j] = Math.abs(generatedID[i]-generatedID[j]);
               }
           }
       }
        return demand;
    }

}
