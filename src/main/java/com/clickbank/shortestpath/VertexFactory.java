package com.clickbank.shortestpath;

public abstract class VertexFactory {

    public static Vertex createVertex() {
        return new Vertex();
    }

}
