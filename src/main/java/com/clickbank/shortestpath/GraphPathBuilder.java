package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class GraphPathBuilder {

    private final HashSet<VertexId> closedSet = new HashSet<>();
    private final PriorityQueue<TrackedVertex> openSet = new PriorityQueue<>(11, new PriorityQueueComparator());

    public void open(@NotNull TrackedVertex vertex) {
        if(!openSet.contains(vertex)) {
            openSet.offer(vertex);
        }
    }

    public boolean isOpen(@NotNull TrackedVertex vertex) {
        return openSet.contains(vertex);
    }

    public boolean hasNext() {
        return !openSet.isEmpty();
    }

    public TrackedVertex closeNext() {
        TrackedVertex next = openSet.poll();
        closedSet.add(next.getId());
        return next;
    }

    public boolean isClosed(@NotNull TrackedVertex vertex) {
        return closedSet.contains(vertex.getId());
    }

    public boolean isClosed(@NotNull VertexId vertex) {
        return closedSet.contains(vertex);
    }

    public Set<VertexId> getVisited() {
        HashSet<VertexId> visited = new HashSet<>(closedSet);
        for(TrackedVertex trackedVertex : openSet) {
            visited.add(trackedVertex.getId());
        }
        return visited;
    }

    public GraphPath getGraphPath(TrackedVertex finish) {
        return GraphPath.createGraphPath(getVisited(), finish);
    }

    public GraphPath getFailedGraphPath() {
        return GraphPath.createFailedGraphPath(getVisited());
    }


    static class PriorityQueueComparator implements Comparator<TrackedVertex> {

        @Override
        public int compare(TrackedVertex o1, TrackedVertex o2) {
            long d1 = Math.round(o1.getHeuristicDistance() * 100.0);
            long d2 = Math.round(o2.getHeuristicDistance() * 100.0);
            int d = (int)(d1 - d2);
            if(d == 0) {
                // Use the traveled distance to break the tie
                d1 = Math.round(o1.getTraveledDistance() * 100.0);
                d2 = Math.round(o2.getTraveledDistance() * 100.0);
                d = (int)(d1 - d2);
            }
            return d;
        }

    }

}
