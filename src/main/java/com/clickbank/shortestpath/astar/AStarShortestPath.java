package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AStarShortestPath implements PathFinder {

    private final Graph graph;
    private final Heuristic heuristic;

    public AStarShortestPath(@NotNull Graph graph, @NotNull Heuristic heuristic) {
        this.graph = graph;
        this.heuristic = heuristic;
    }

    @Override
    @NotNull
    public GraphPath findPath(@NotNull VertexId start, @NotNull VertexId finish) {
        AStarTrackedVertexFactory trackedVertexFactory = new AStarTrackedVertexFactory(heuristic, finish);
        GraphPathBuilder builder = new GraphPathBuilder();

        builder.open(trackedVertexFactory.createTrackedVertex(start));
        while(builder.hasNext()) {
            TrackedVertex current = builder.closeNext();
            if(current.getId().equals(finish)) {
                return builder.getGraphPath(current);
            }

            for(VertexId vertex : graph.getNeighbors(current.getId())) {
                TrackedVertex neighbor = trackedVertexFactory.createNeighboringTrackedVertex(current, vertex);
                if(!builder.isClosed(neighbor)) {
                    builder.open(neighbor);
                }
            }
        }

        return builder.getFailedGraphPath();
    }

}
