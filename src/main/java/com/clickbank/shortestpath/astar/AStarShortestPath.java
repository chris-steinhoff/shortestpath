package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.GraphPath;
import com.clickbank.shortestpath.Heuristic;
import com.clickbank.shortestpath.TrackedVertex;
import com.clickbank.shortestpath.Vertex;
import com.clickbank.shortestpath.VertexId;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AStarShortestPath {

    private final Heuristic heuristic;

    public AStarShortestPath(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Iterable<TrackedVertex> getPath(@NotNull Vertex start, @NotNull Vertex finish) {
        HashSet<VertexId> closedSet = new HashSet<>();
        PriorityQueue<AStarVertex> openSet = new PriorityQueue<>(11, new AStarPriorityQueueComparator());

        AStarVertex s = new AStarVertex(start, 0, heuristic.distanceBetween(start.getId(), finish.getId()), true, null);
        openSet.add(s);

        while(!openSet.isEmpty()) {
            AStarVertex current = openSet.poll();
            if(current.getId().equals(finish.getId())) {
                return new GraphPath(current);
            }

            closedSet.add(current.getId());
            for(Vertex vertex : current.getNeighbors()) {
                if(closedSet.contains(vertex.getId())) {
                    continue;
                }

                double traveledDistance = current.getTraveledDistance() + 1;
                double heuristicDistance = traveledDistance + heuristic.distanceBetween(vertex.getId(), finish.getId());
                AStarVertex neighbor = new AStarVertex(vertex, traveledDistance, heuristicDistance, true, current);
                if(!openSet.contains(neighbor)/* || tentativeGScore < neighbor.gScore*/) {
                    openSet.offer(neighbor);
                }
            }
        }

        return null;
    }

}
