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
        AStarContext context = this.context;

        context.open(trackedVertexFactory.createTrackedVertex(start));
        while(context.hasNext()) {
            TrackedVertex current = context.closeNext();
            if(current.getId().equals(finish)) {
                return context.getGraphPath(current);
            }

            for(VertexId vertex : graph.getNeighbors(current.getId())) {
                TrackedVertex neighbor = trackedVertexFactory.createNeighboringTrackedVertex(current, vertex);
                context.openIfNotClosed(neighbor);
            }
        }

        return context.getFailedGraphPath();
    }

    public void setContext(@NotNull AStarContext context) {
        this.context = context;
    }

}
