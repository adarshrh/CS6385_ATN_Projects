package project2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class NetworkReliabilityFinder {


    public static double findReliability(double p, int k)
    {
        //list of k distinct random numbers between 0 and 1024 are generated
        ArrayList<Integer> listOfKRandomSystemStates = new ArrayList<Integer>();

        for(int j = 0; j < k; j++) {

            Random rand = new Random();

            int randomNumber = rand.nextInt(1024);
            //System.out.println("Random no" +randomNumber);

            while(listOfKRandomSystemStates.contains(randomNumber)){
                randomNumber = rand.nextInt(1024);
            }
            listOfKRandomSystemStates.add(randomNumber);
        }


        double R =0;

		 /*
        All combinations involved in the network can be represented by the numbers from 0 to 1023 as follows
        0 - 0000000000
        1 - 0000000001 - one link is up
        2 - 0000000010 - a different link is up
        3 - 0000000011 - two different links are up
        ...
        1023 - 1111111111 - all links are up!

        */

        for(int i = 0; i < 1024; i++) {


            Graph Graph= new Graph();

            Graph.p = p;
            //All possible combinations are generated using all 10 digit binary numbers
            String systemState = String.format("%10s", Integer.toBinaryString(i)).replace(" ", "0");
            //System.out.println(systemState);

            for(int j = 0; j < 10; j++){

                if (systemState.charAt(j) =='1'){

                    Graph.adjMatrix[Graph.edgeToNodesMap[j][0]][Graph.edgeToNodesMap[j][1]]= 0;
                    Graph.adjMatrix[Graph.edgeToNodesMap[j][1]][Graph.edgeToNodesMap[j][0]]= 0;

                }
            }

            //if i (current system state) is in the list of randomly chosen system states then,
            //flip or reverse the system condition(or state)
            //else do not reverse state
            if(listOfKRandomSystemStates.contains(i)){
                if(!Graph.isGraphConnected()){
                    //compute reliability by adding reliability of different states
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

        for ( p = 0.05; p <1.05; p += 0.05) {
            double r = findReliability(p,k);
            fw.write(p+","+r);
            fw.newLine();
        }
        fw.close();
        System.out.println();
        fw = new BufferedWriter(new FileWriter("output2.csv"));
        p=0.9;
        for(k = 0; k <= 20; k++){
            double r = 0;
            for(int j = 0;j < 100; j++){
                r = r +findReliability(p, k);
            }
            r = r/100;
            fw.write(k+","+r);
            fw.newLine();

        }
        fw.close();

    }
}
