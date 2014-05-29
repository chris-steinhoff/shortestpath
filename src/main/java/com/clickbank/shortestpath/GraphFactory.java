package com.clickbank.shortestpath;

public abstract class GraphFactory {

    public static Graph createGraph() {
        return new Graph();
    }

}
