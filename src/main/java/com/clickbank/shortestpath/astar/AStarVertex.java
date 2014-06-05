package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.TrackedVertex;
import com.clickbank.shortestpath.Vertex;
import com.clickbank.shortestpath.VertexId;

public class AStarVertex implements TrackedVertex {

    private final Vertex vertex;
    private final double traveledDistance;
    private final double heuristicDistance;
    private final boolean wasVisited;
    private final AStarVertex previousVertex;

    public AStarVertex(Vertex vertex, double traveledDistance, double heuristicDistance, boolean wasVisited,
                       AStarVertex previousVertex) {
        this.vertex = vertex;
        this.heuristicDistance = heuristicDistance;
        this.traveledDistance = traveledDistance;
        this.wasVisited = wasVisited;
        this.previousVertex = previousVertex;
    }

    @Override
    public int hashCode() {
        return vertex.getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof AStarVertex)) return false;

        return getId().equals(((AStarVertex)o).getId());
    }

    @Override
    public String toString() {
        return vertex.toString();
    }

    public VertexId getId() {
        return vertex.getId();
    }

    @Override
    public double getTraveledDistance() {
        return traveledDistance;
    }

    @Override
    public double getHeuristicDistance() {
        return heuristicDistance;
    }

    @Override
    public boolean getWasVisited() {
        return wasVisited;
    }

    @Override
    public AStarVertex getPreviousVertex() {
        return previousVertex;
    }

    @Override
    public Iterable<Vertex> getNeighbors() {
        return vertex.getNeighbors();
    }

}
