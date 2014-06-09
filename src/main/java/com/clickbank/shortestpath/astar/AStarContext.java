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

    protected final Object lock = new Object();
    @GuardedBy("lock")
    protected final HashSet<VertexId> closedSet = new HashSet<>();
    @GuardedBy("lock")
    protected final PriorityQueue<TrackedVertex> openSet = new PriorityQueue<>(11, new AStarTrackedVertexComparator());

    public void open(@NotNull TrackedVertex vertex) {
        synchronized(lock) {
            if(!openSet.contains(vertex)) {
                openSet.offer(vertex);
            }
        }
    }

    public boolean hasNext() {
        synchronized(lock) {
            return !openSet.isEmpty();
        }
    }

    public TrackedVertex closeNext() {
        TrackedVertex next;
        synchronized(lock) {
            next = openSet.poll();
            closedSet.add(next.getId());
        }
        return next;
    }

    public void openIfNotClosed(TrackedVertex neighbor) {
        synchronized(lock) {
            if(!closedSet.contains(neighbor.getId()) && !openSet.contains(neighbor)) {
                openSet.offer(neighbor);
            }
        }
    }

    public Set<VertexId> getVisited() {
        HashSet<VertexId> visited;
        synchronized(lock) {
            visited = new HashSet<>(closedSet);
            for(TrackedVertex trackedVertex : openSet) {
                visited.add(trackedVertex.getId());
            }
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
