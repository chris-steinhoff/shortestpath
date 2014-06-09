package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GraphPath implements Iterable<VertexId> {

    private final List<VertexId> path;
    private final Set<VertexId> visitedVertices;
    private final boolean pathFound;
    private final Set<VertexId> pathVertices;

    private GraphPath(List<VertexId> path, Set<VertexId> visitedVertices, boolean pathFound) {
        this.path = path;
        this.visitedVertices = visitedVertices;
        this.pathFound = pathFound;
        this.pathVertices = new HashSet<>(path);
    }

    public static GraphPath createGraphPath(
            @NotNull Set<VertexId> visitedVertices,
            @NotNull TrackedVertex finish) {
        LinkedList<VertexId> path = new LinkedList<>();
        TrackedVertex current = finish;
        do {
            path.addFirst(current.getId());
        } while((current = current.getPreviousVertex()) != null);
        return new GraphPath(path, visitedVertices, true);
    }

    public static GraphPath createFailedGraphPath(
            @NotNull Set<VertexId> visitedVertices) {
        return new GraphPath(new ArrayList<VertexId>(0), visitedVertices, false);
    }

    public boolean wasVertexVisited(VertexId id) {
        return visitedVertices.contains(id);
    }

    public boolean isVertexInPath(VertexId id) {
        return pathVertices.contains(id);
    }

    public boolean wasPathFound() {
        return pathFound;
    }

    @Override
    public Iterator<VertexId> iterator() {
        return path.iterator();
    }

}
