package com.clickbank.shortestpath;

import java.util.Set;

public class Vertex implements Comparable<Vertex> {

    private final String id;
    private Set<Vertex> neighbors;

    public Vertex(String id) {
        if(id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;
    }

    public void addNeighbor(Vertex vertex) {
        if(vertex != null) {
            neighbors.add(vertex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex)o;

        return id.equals(vertex.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Vertex o) {
        return id.compareTo(o.id);
    }

}
