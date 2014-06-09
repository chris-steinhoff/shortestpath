package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.Heuristic;
import com.clickbank.shortestpath.TrackedVertex;
import com.clickbank.shortestpath.TrackedVertexFactory;
import com.clickbank.shortestpath.VertexId;

public class AStarTrackedVertexFactory implements TrackedVertexFactory {

    private final Heuristic heuristic;
    private final VertexId finish;

    public AStarTrackedVertexFactory(Heuristic heuristic, VertexId finish) {
        this.heuristic = heuristic;
        this.finish = finish;
    }

    @Override
    public TrackedVertex createTrackedVertex(VertexId vertex) {
        return createNeighboringTrackedVertex(null, vertex);
    }

    @Override
    public TrackedVertex createNeighboringTrackedVertex(TrackedVertex parent, VertexId neighbor) {
        double traveledDistance = (parent == null ? 0.0 : parent.getTraveledDistance() + 1);
        double heuristicDistance = traveledDistance + heuristic.distanceBetween(neighbor, finish);
        return new TrackedVertex(neighbor, traveledDistance, heuristicDistance, parent);
    }

}
