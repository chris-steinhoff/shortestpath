package com.clickbank.shortestpath;

public interface PrintTarget {

    void printObstacle();

    void printPathVertex(VertexId vertex);

    void printVertex(VertexId vertex);

    void printVisitedVertex(VertexId vertex);

    void printNewLine();

}
