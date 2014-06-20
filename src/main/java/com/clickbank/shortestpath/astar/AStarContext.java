package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.GraphPath;
import com.clickbank.shortestpath.TrackedVertex;
import com.clickbank.shortestpath.VertexId;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

@ThreadSafe
public class AStarContext {

    @GuardedBy("this")
    protected final HashSet<VertexId> closedSet = new HashSet<>();
    @GuardedBy("this")
    protected final PriorityQueue<TrackedVertex> openSet =
            new PriorityQueue<>(11, new AStarTrackedVertexComparator());

    public synchronized void open(@NotNull TrackedVertex vertex) {
        if(!openSet.contains(vertex)) {
            openSet.offer(vertex);
        }
    }

    public synchronized boolean hasNext() {
        return !openSet.isEmpty();
    }

    public synchronized TrackedVertex closeNext() {
        TrackedVertex next = openSet.poll();
        closedSet.add(next.getId());
        return next;
    }

    public synchronized void openIfNotClosed(TrackedVertex neighbor) {
        if(!closedSet.contains(neighbor.getId()) && !openSet.contains(neighbor)) {
            openSet.offer(neighbor);
        }
    }

    public synchronized Set<VertexId> getVisited() {
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

}
