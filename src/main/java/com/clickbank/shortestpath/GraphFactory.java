package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public abstract class GraphFactory {

    private static final Charset utf8 = Charset.forName("UTF-8");

    @NotNull
    public static GraphData createGraph(String filename) throws IOException {
        File file = new File(filename);

        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        if(file.length() == 0) {
            throw new IllegalArgumentException("file is empty");
        }

        List<String> rows = Files.readAllLines(file.toPath(), utf8);

        HashMap<String,Vertex> vertexCache = new HashMap<>();

        Graph graph = new Graph();
        Vertex start = null, finish = null;

        for(int rowNum = 0 ; rowNum < rows.size() ; ++rowNum) {
            String row = rows.get(rowNum);
            char[] columns = row.toCharArray();
            for(int colNum = 0 ; colNum < columns.length ; ++colNum) {
                char cell = Character.toUpperCase(columns[colNum]);

                Vertex vertex = createVertex(rowNum, colNum);

                switch(cell) {
                    case 'S': // Starting cell
                        if(start != null) {
                            throw new IllegalArgumentException("only one starting point is allowed");
                        }
                        start = vertex;
                        break;
                    case 'F': // Finishing cell
                        if(finish != null) {
                            throw new IllegalArgumentException("only one finishing point is allowed");
                        }
                        finish = vertex;
                        break;
                    case 'O': // Normal cell
                        break;
                    default: // Everything else is non-traversable
                        continue;
                }

                Vertex neighbor = vertexCache.get(createVertexId(rowNum - 1, colNum));
                if(neighbor != null) {
                    vertex.addNeighbor(neighbor);
//                    System.out.println(vertex + " --> " + neighbor);
                }
                neighbor = vertexCache.get(createVertexId(rowNum, colNum - 1));
                if(neighbor != null) {
                    vertex.addNeighbor(neighbor);
//                    System.out.println(vertex + " --> " + neighbor);
                }

                vertexCache.put(vertex.getId(), vertex);
                graph.addVertex(vertex);
            }
        }

        return new GraphData(graph, start, finish);
    }

    @NotNull
    private static Vertex createVertex(int row, int column) {
        return new Vertex(createVertexId(row, column));
    }

    @NotNull
    private static String createVertexId(int row, int column) {
        return row + ":" + column;
    }

}
