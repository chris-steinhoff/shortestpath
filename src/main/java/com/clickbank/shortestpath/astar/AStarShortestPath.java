package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.*;
import org.jetbrains.annotations.NotNull;

public class AStarShortestPath implements PathFinder {

    protected final Graph graph;
    protected final Heuristic heuristic;

    protected AStarContext context = new AStarContext();

    public AStarShortestPath(@NotNull Graph graph, @NotNull Heuristic heuristic) {
        this.graph = graph;
        this.heuristic = heuristic;
    }

    @Override
    @NotNull
    public GraphPath findPath(@NotNull VertexId start, @NotNull VertexId finish) {
        AStarTrackedVertexFactory trackedVertexFactory = new AStarTrackedVertexFactory(heuristic, finish);
        AStarContext builder = this.context;

        builder.open(trackedVertexFactory.createTrackedVertex(start));
        while(builder.hasNext()) {
            TrackedVertex current = builder.closeNext();
            if(current.getId().equals(finish)) {
                return builder.getGraphPath(current);
            }

            for(VertexId vertex : graph.getNeighbors(current.getId())) {
                TrackedVertex neighbor = trackedVertexFactory.createNeighboringTrackedVertex(current, vertex);
                builder.openIfNotClosed(neighbor);
            }
        }

        return builder.getFailedGraphPath();
    }

    public void setContext(@NotNull AStarContext context) {
        this.context = context;
    }

}
