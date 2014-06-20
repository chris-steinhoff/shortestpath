package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.GraphPath;
import com.clickbank.shortestpath.TrackedVertex;
import com.clickbank.shortestpath.VertexId;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@ThreadSafe
public class AStarContextSkipList extends AStarContext {

    @GuardedBy("this")
    private final ConcurrentSkipListSet<TrackedVertex> openSet =
            new ConcurrentSkipListSet<>(new AStarTrackedVertexComparator());

    public synchronized void open(@NotNull TrackedVertex vertex) {
        if(!openSet.contains(vertex)) {
            openSet.add(vertex);
        }
    }

    public synchronized boolean hasNext() {
        return !openSet.isEmpty();
    }

    public synchronized TrackedVertex closeNext() {
        TrackedVertex next = openSet.pollFirst();
        closedSet.add(next.getId());
        return next;
    }

    public synchronized void openIfNotClosed(TrackedVertex neighbor) {
        if(!closedSet.contains(neighbor.getId()) && !openSet.contains(neighbor)) {
            openSet.add(neighbor);
        }
    }

    public synchronized Set<VertexId> getVisited() {
        HashSet<VertexId> visited = new HashSet<>(closedSet);
        for(TrackedVertex trackedVertex : openSet) {
            visited.add(trackedVertex.getId());
        }
        return visited;
    }

}
