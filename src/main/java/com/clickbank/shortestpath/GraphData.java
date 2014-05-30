package com.clickbank.shortestpath;

public class GraphData {

    public Graph graph;
    public Vertex start;
    public Vertex finish;

    public GraphData(Graph graph, Vertex start, Vertex finish) {
        this.graph = graph;
        this.start = start;
        this.finish = finish;
    }

}
