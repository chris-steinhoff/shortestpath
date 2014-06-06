package com.clickbank.shortestpath;

import com.clickbank.shortestpath.astar.AStarShortestPath;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Graph {

    private final Map<VertexId,Vertex> vertices;

    public Graph(@NotNull Map<VertexId,Vertex> vertices) {
        this.vertices = vertices;
    }

    public void printToTerminal() {
        int maxX = -1, maxY = -1;

        for(VertexId id : vertices.keySet()) {
            maxX = Math.max(maxX, (int)id.getX());
            maxY = Math.max(maxY, (int)id.getY());
        }

        for(int r = 0 ; r <= maxX ; ++r) {
            for(int c = 0 ; c <= maxY ; ++c) {
                Vertex vertex = vertices.get(new VertexId(r, c));
                System.out.print(vertex == null ? 'X' : 'O');
            }
            System.out.println();
        }
    }

    public void printShortestPathToTerminal(VertexId start, VertexId finish) {
        int maxX = -1, maxY = -1;

        for(VertexId id : vertices.keySet()) {
            maxX = Math.max(maxX, (int)id.getX());
            maxY = Math.max(maxY, (int)id.getY());
        }

        Vertex s = vertices.get(start), f = vertices.get(finish);

        AStarShortestPath aStar = new AStarShortestPath(new ManhattanHeuristic());
        Iterable<TrackedVertex> path = aStar.findPath(s, f);

        HashMap<VertexId,TrackedVertex> pathVertices = new HashMap<>();
        for(TrackedVertex trackedVertex : path) {
            pathVertices.put(trackedVertex.getId(), trackedVertex);
        }

        for(int r = 0 ; r <= maxX ; ++r) {
            for(int c = 0 ; c <= maxY ; ++c) {
                VertexId id = new VertexId(r, c);
                char n = 'X';
                if(vertices.containsKey(id)) {
                    if(pathVertices.containsKey(id)) {
                        n = 'P';
                    } else {
                        n = 'O';
                    }
                }
                System.out.print(n);
            }
            System.out.println();
        }
    }

}
