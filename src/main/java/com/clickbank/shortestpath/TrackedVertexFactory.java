package com.clickbank.shortestpath;

public interface TrackedVertexFactory {

    TrackedVertex createTrackedVertex(VertexId vertex);

    TrackedVertex createNeighboringTrackedVertex(TrackedVertex parent, VertexId neighbor);

}
