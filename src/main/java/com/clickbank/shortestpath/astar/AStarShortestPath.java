package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AStarShortestPath implements PathFinder {

    private final Heuristic heuristic;

    public AStarShortestPath(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Iterable<TrackedVertex> findPath(@NotNull Vertex start, @NotNull Vertex finish) {
        HashSet<VertexId> closedSet = new HashSet<>();
        PriorityQueue<AStarVertex> openSet = new PriorityQueue<>(11, new AStarPriorityQueueComparator());

        AStarVertex current = createAStarVertex(start, finish);
        openSet.add(current);

        while(!openSet.isEmpty()) {
            current = openSet.poll();
            if(current.getId().equals(finish.getId())) {
                return new GraphPath(current);
            }

            closedSet.add(current.getId());
            for(Vertex vertex : current.getNeighbors()) {
                if(closedSet.contains(vertex.getId())) {
                    continue;
                }

                AStarVertex neighbor = createNeighboringAStarVertex(current, vertex, finish);
                if(!openSet.contains(neighbor)/* || tentativeGScore < neighbor.gScore*/) {
                    openSet.offer(neighbor);
                }
            }
        }

        return null;
    }

    private AStarVertex createAStarVertex(@NotNull Vertex start, @NotNull Vertex finish) {
        return createNeighboringAStarVertex(null, start, finish);
    }

    private AStarVertex createNeighboringAStarVertex(@Nullable AStarVertex current,
            @NotNull Vertex neighbor, @NotNull Vertex finish) {
        double traveledDistance = (current == null ? 0.0 : current.getTraveledDistance() + 1);
        double heuristicDistance = traveledDistance + heuristic.distanceBetween(neighbor.getId(), finish.getId());
        return new AStarVertex(neighbor, traveledDistance, heuristicDistance, true, current);
    }

}
