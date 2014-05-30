package com.clickbank.shortestpath;

import java.util.Map;

public class Graph {

    private Map<String,Vertex> vertices;

    public Graph() {
    }

    public void addVertex(int row, int column) {
        String id = row + ":" + column;
        vertices.put(id, new Vertex(row, column));
    }

}
