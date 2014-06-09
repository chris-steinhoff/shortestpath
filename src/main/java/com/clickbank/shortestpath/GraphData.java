package com.clickbank.shortestpath;

public class GraphData {

    public Graph graph;
    public VertexId start;
    public VertexId finish;

    public GraphData(Graph graph, VertexId start, VertexId finish) {
        this.graph = graph;
        this.start = start;
        this.finish = finish;
    }

}
