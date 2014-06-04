package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final Set<Vertex> vertices;

    public Graph() {
        vertices = new HashSet<>();
    }

    public void addVertex(@NotNull Vertex vertex) {
        vertices.add(vertex);
    }

}
