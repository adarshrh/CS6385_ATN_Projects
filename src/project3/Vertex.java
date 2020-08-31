package project3;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    Map<Integer,Edge> edgeMap;
    int vertexNum;
    int x;
    int y;

    Vertex(int vertexNum, int x, int y){
        this.vertexNum = vertexNum;
        this.x = x;
        this.y = y;
        edgeMap = new HashMap<>();
    }
    public Map<Integer, Edge> getEdgeMap() {
        return edgeMap;
    }

    public void setEdgeMapt(Map<Integer, Edge> edgeMap) {
        this.edgeMap = edgeMap;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public void setVertexNum(int vertexNum) {
        this.vertexNum = vertexNum;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDegree(){
        return edgeMap.size();
    }
    public int hashCode() {
        return vertexNum;
    }
}
