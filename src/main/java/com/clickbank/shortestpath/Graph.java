package com.clickbank.shortestpath;

import com.clickbank.shortestpath.astar.AStarShortestPath;
import com.clickbank.shortestpath.grid.GridGraphPrinter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph implements Iterable<VertexId> {

    private final Map<VertexId,Vertex> vertices;

    public Graph(@NotNull Map<VertexId,Vertex> vertices) {
        this.vertices = vertices;
    }

    public Vertex getVertex(VertexId id) {
        return vertices.get(id);
    }

    public boolean containsVertex(VertexId id) {
        return vertices.containsKey(id);
    }

    public Set<VertexId> getNeighbors(VertexId id) {
        Vertex vertex = vertices.get(id);
        return vertex == null ? new HashSet<VertexId>(0) : vertex.getNeighbors();
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

    @Override
    public Iterator<VertexId> iterator() {
        return vertices.keySet().iterator();
    }

    public void printShortestPathToTerminal(VertexId start, VertexId finish) {

        AStarShortestPath aStar = new AStarShortestPath(this, new ManhattanHeuristic());
        GraphPath path = aStar.findPath(start, finish);
        GraphPathPrinter printer = new GridGraphPrinter(this);
        printer.printGraphPath(path, new TerminalPrintTarget());

    }

}
