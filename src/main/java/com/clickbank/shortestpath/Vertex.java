package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class Vertex implements Comparable<Vertex> {

    private final String id;
    private final Set<Vertex> neighbors;

    public Vertex(@NotNull String id) {
        this.id = id;
        neighbors = new HashSet<>();
    }

    public void addNeighbors(@NotNull Iterable<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            neighbors.add(vertex);
            vertex.neighbors.add(this);
            System.out.println(this + " --> " + vertex);
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
    public int compareTo(@NotNull Vertex o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

}
