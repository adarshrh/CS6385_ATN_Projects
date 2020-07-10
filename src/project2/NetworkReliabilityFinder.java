package project2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NetworkReliabilityFinder {

    /**
     * Calculate network reliability for given p and k value
     * @param p is link reliability
     * @param k is number of random network states
     * @return
     */
    public static double findReliability(double p, int k)
    {
        Set<Integer> randomStateSet = new HashSet<>();
        for(int j = 0; j < k; j++) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(1024);
            while(randomStateSet.contains(randomNumber)){
                randomNumber = rand.nextInt(1024);
            }
            randomStateSet.add(randomNumber);
        }


        double R =0;
        for(int i = 0; i < 1024; i++) {
            Graph Graph= new Graph();
            Graph.p = p;
            String linkStates = String.format("%10s", Integer.toBinaryString(i)).replace(" ", "0");
            for(int j = 0; j < 10; j++){
                if (linkStates.charAt(j) =='1'){
                    Graph.adjMatrix[Graph.edgeToNodesMap[j][0]][Graph.edgeToNodesMap[j][1]]= 0;
                    Graph.adjMatrix[Graph.edgeToNodesMap[j][1]][Graph.edgeToNodesMap[j][0]]= 0;
                }
            }


            if(randomStateSet.contains(i)){
                if(!Graph.isGraphConnected()){
                    R = R + Graph.calculateReliability();
                }
            }
            else{
                if(Graph.isGraphConnected()){

                    R = R + Graph.calculateReliability();
                }
            }
        }

        return R;
    }


    public static void main(String[] args) throws IOException {

        int k=0;
        double p;
        BufferedWriter fw = new BufferedWriter(new FileWriter("output1.csv"));
    // Find the network reliability for different values of p
        for ( p = 0.05; p <1.05; p += 0.05) {
            double r = findReliability(p,k);
            fw.write(p+","+String.format("%.3f", r));
            fw.newLine();
            System.out.println("p="+String.format("%2f",p)+" reliability="+String.format("%.3f", r));
        }

        fw.close();
        System.out.println();
        fw = new BufferedWriter(new FileWriter("output2.csv"));
        // Find the network reliability for different values of k with fixed p=0.9
        p=0.9;
        for(k = 0; k <= 20; k++){
            double r = 0;
            for(int j = 0;j < 150; j++){
                r = r +findReliability(p, k);
            }
            r = r/150;
            fw.write(k+","+String.format("%.3f", r));
            fw.newLine();
            System.out.println("k="+k+" reliability="+String.format("%.3f", r));

        }
        fw.close();

    }
}
