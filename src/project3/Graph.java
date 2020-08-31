package project3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {
    HashMap<Integer,Vertex> vertexMap;
    List<Edge> edgeList;
    int n;

    Graph(int n){
        this.n = n;
        Random random = new Random();
        vertexMap = new HashMap<>();
        edgeList = new ArrayList<>();
        for(int i=1;i<=n;i++){
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            vertexMap.put(i,new Vertex(i,x,y));
        }
    }

    public void createCompleteGraph(){
        int edgeCount=1;
        for(int i=1;i<=n;i++){
            for(int j=i+1;j<=n;j++){
               Edge e1 = new Edge(vertexMap.get(i),vertexMap.get(j),edgeCount);
                vertexMap.get(i).getEdgeMap().put(edgeCount,e1);
               // edgeCount++;
                Edge e2 = new Edge(vertexMap.get(j),vertexMap.get(i),edgeCount);
                vertexMap.get(j).getEdgeMap().put(edgeCount,e2);
                edgeCount++;
                edgeList.add(e1);
            }
        }
        Collections.sort(edgeList, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return ((Double)o1.getDistance()).compareTo( o2.getDistance());
            }
        });
    }

    public void computeAllEdges(){
        int edgeCount=1;
        for(int i=1;i<=n;i++){
            for(int j=i+1;j<=n;j++){
                Edge e1 = new Edge(vertexMap.get(i),vertexMap.get(j),edgeCount);
                edgeCount++;
                edgeList.add(e1);
            }
        }
        Collections.sort(edgeList, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return ((Double)o1.getDistance()).compareTo( o2.getDistance());
            }
        });
    }

    public double deletionHeuristics(){
        createCompleteGraph();
        int index = edgeList.size()-1;
        double cost=0;
        while(index>=0){
            Edge edge = edgeList.get(index);
            int fromNode = edge.from.vertexNum;
            int toNode = edge.to.vertexNum;
            Edge edge1 = vertexMap.get(fromNode).getEdgeMap().remove(edge.edgeNum);
            Edge edge2 = vertexMap.get(toNode).getEdgeMap().remove(edge.edgeNum);
            if(diameterAndDegreeCheck()){
                //System.out.println("index="+index);
                edgeList.set(index,null);
            } else{
                vertexMap.get(fromNode).getEdgeMap().put(edge1.edgeNum,edge1);
                vertexMap.get(toNode).getEdgeMap().put(edge2.edgeNum,edge2);
            }
            index--;
        }
        for(Edge edge: edgeList){
            if(edge!=null){
              //  System.out.println("edge: "+edge.from.vertexNum+","+edge.to.vertexNum+ " :"+edge.distance);
                cost+=edge.distance;
            }
        }
        return cost;
    }
    public boolean diameterAndDegreeCheck(){
        Iterator<Integer> itr = vertexMap.keySet().iterator();
        while (itr.hasNext()){
            int node = itr.next();
            if(vertexMap.get(node).getEdgeMap().size()<3)
                return false;
        }

        itr =  vertexMap.keySet().iterator();
        while (itr.hasNext()){
            int node = itr.next();
            if(!bfs(node)){
                return false;
            }
        }
        return true;
    }

    public boolean bfs(int s){
        Set<Integer> visited = new HashSet<>();
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited.add(s);
        queue.add(s);
        int hopCount=0;
        while (queue.size() != 0 && hopCount<4 )
        {
            s = queue.poll();
            for (Map.Entry<Integer, Edge> entry : vertexMap.get(s).getEdgeMap().entrySet()){
                int toNode = entry.getValue().to.vertexNum;
                if (!visited.contains(toNode))
                {
                    visited.add(toNode);
                    queue.add(toNode);
                }
            }
            hopCount++;


        }

        if(visited.size()==n)
            return true;
        return false;
    }

    public double constructionHeuristics(){
        computeAllEdges();
        int index = 0;
        double cost=0;
        List<Edge> result = new ArrayList<>();
        while(index < edgeList.size()){
            Edge edge = edgeList.get(index);
            result.add(edge);
            int fromNode = edge.from.vertexNum;
            int toNode = edge.to.vertexNum;
            Edge edge1 = new Edge(edge.from,edge.to,edge.edgeNum);
            Edge edge2 = new Edge(edge.to,edge.from,edge.edgeNum);
            vertexMap.get(fromNode).getEdgeMap().put(edge1.edgeNum,edge1);
            vertexMap.get(toNode).getEdgeMap().put(edge2.edgeNum,edge2);
            if(diameterAndDegreeCheck()){
               break;
            }
            index++;
        }
        for(Edge edge: result){
            if(edge!=null){
                //  System.out.println("edge: "+edge.from.vertexNum+","+edge.to.vertexNum+ " :"+edge.distance);
                cost+=edge.distance;
            }
        }
        edgeList = result;
        return cost;
    }

    public static void main(String[] arg) throws IOException {
        for(int n=20;n<25;n++){
            Graph g = new Graph(n);
            System.out.println("Deletion algorithm n= "+n+" cost= "+ g.deletionHeuristics());

            if(n==20 || n==24){
                BufferedWriter fw = new BufferedWriter(new FileWriter("deletionAlgo_n_"+n+"_graph.csv"));
                for(Edge edge: g.edgeList){
                    if(edge!=null){
                      //  System.out.println("edge: "+edge.from.vertexNum+","+edge.to.vertexNum+ " :"+edge.distance);
                        fw.write(edge.from.vertexNum+","+edge.to.vertexNum+ ","+ edge.distance);
                        fw.newLine();
                    }
                }
                fw.close();
            }

        }

        for(int n=20;n<25;n++){
            Graph g = new Graph(n);
            System.out.println("Construction Algorithm n= "+n+" cost= "+ g.constructionHeuristics());

            if(n==20 || n==24){
                BufferedWriter fw = new BufferedWriter(new FileWriter("constructionAlgo_n_"+n+"_graph.csv"));
                for(Edge edge: g.edgeList){
                    if(edge!=null){
                        //System.out.println("edge: "+edge.from.vertexNum+","+edge.to.vertexNum+ " :"+edge.distance);
                        fw.write(edge.from.vertexNum+","+edge.to.vertexNum+ ","+ edge.distance);
                        fw.newLine();
                    }
                }
                fw.close();
            }

        }


    }
}
