package com.clickbank.shortestpath;

public interface TrackedVertex {

    double getHeuristicDistance();

    double getTraveledDistance();

    boolean getWasVisited();

    TrackedVertex getPreviousVertex();

    Iterable<Vertex> getNeighbors();

}
