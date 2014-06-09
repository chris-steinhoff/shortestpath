package com.clickbank.shortestpath.grid;

import com.clickbank.shortestpath.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GridGraphPrinter implements GraphPathPrinter {

    private final Graph graph;

    public GridGraphPrinter(Graph graph) {
        this.graph = graph;
    }

    //    @Override
    public void printGraphPath(@NotNull GraphPath path, @NotNull PrintTarget target) {
        int maxX = -1, maxY = -1;

        for(VertexId id : graph) {
            maxX = Math.max(maxX, (int)id.getX());
            maxY = Math.max(maxY, (int)id.getY());
        }
        /*HashMap<VertexId,VertexId> pathVertices = new HashMap<>();
        for(VertexId trackedVertex : path) {
            pathVertices.put(trackedVertex, trackedVertex);
        }*/

        for(int r = 0 ; r <= maxX ; ++r) {
            for(int c = 0 ; c <= maxY ; ++c) {
                VertexId id = new VertexId(r, c);
                if(graph.containsVertex(id)) {
                    if(path.isVertexInPath(id)) {
                        target.printPathVertex(id);
                    } else {
                        if(path.wasVertexVisited(id)) {
                            target.printVisitedVertex(id);
                        } else {
                            target.printVertex(id);
                        }
                    }
                } else {
                    target.printObstacle();
                }
            }
            target.printNewLine();
        }
    }

}
