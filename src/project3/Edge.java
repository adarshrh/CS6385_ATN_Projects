package project3;

public class Edge {
    Vertex from;
    Vertex to;
    double distance;
    int edgeNum;

    Edge(Vertex from, Vertex to, int edgeNum){
        this.from = from;
        this.to = to;
        this.edgeNum = edgeNum;
        distance = Math.sqrt(Math.pow((from.x - to.x),2) + Math.pow((from.y - to.y),2));
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
