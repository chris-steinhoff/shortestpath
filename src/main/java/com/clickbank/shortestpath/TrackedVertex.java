package com.clickbank.shortestpath;

public interface TrackedVertex {

    VertexId getId();

    double getHeuristicDistance();

    double getTraveledDistance();

    boolean getWasVisited();

    TrackedVertex getPreviousVertex();

    Iterable<Vertex> getNeighbors();

}
