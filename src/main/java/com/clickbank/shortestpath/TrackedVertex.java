package com.clickbank.shortestpath;

public class TrackedVertex {

    private final VertexId id;
    private final double traveledDistance;
    private final double heuristicDistance;
    private final TrackedVertex previousVertex;

    public TrackedVertex(VertexId id, double traveledDistance, double heuristicDistance, TrackedVertex previousVertex) {
        this.id = id;
        this.traveledDistance = traveledDistance;
        this.heuristicDistance = heuristicDistance;
        this.previousVertex = previousVertex;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        VertexId objId;
        if(obj instanceof TrackedVertex) {
            objId = ((TrackedVertex)obj).getId();
        } else if(obj instanceof Vertex) {
            objId = ((Vertex)obj).getId();
        } else if(obj instanceof VertexId) {
            objId = (VertexId)obj;
        } else {
            return false;
        }
        return id.equals(objId);
    }

    public VertexId getId() {
        return id;
    }

    public double getTraveledDistance() {
        return traveledDistance;
    }

    public double getHeuristicDistance() {
        return heuristicDistance;
    }

    public TrackedVertex getPreviousVertex() {
        return previousVertex;
    }

}
