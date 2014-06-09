package com.clickbank.shortestpath.grid;

import com.clickbank.shortestpath.Graph;
import com.clickbank.shortestpath.GraphData;
import com.clickbank.shortestpath.Vertex;
import com.clickbank.shortestpath.VertexId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GridGraphFactory {

    private static final Charset utf8 = Charset.forName("UTF-8");
    private HashMap<VertexId,Vertex> vertexCache = new HashMap<>();
    private Vertex start;
    private Vertex finish;

    private GridGraphFactory() {
    }

    @NotNull
    public static GraphData createGraph(String filename) throws IOException {
        File file = new File(filename);
        GridGraphFactory factory = new GridGraphFactory();
        HashMap<VertexId,Vertex> vertices = new HashMap<>();

        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        if(file.length() == 0) {
            throw new IllegalArgumentException("file is empty");
        }

        List<String> rows = Files.readAllLines(file.toPath(), utf8);

        for(int rowNum = 0 ; rowNum < rows.size() ; ++rowNum) {
            String row = rows.get(rowNum);

            char[] columns = row.toCharArray();
            for(int colNum = 0 ; colNum < columns.length ; ++colNum) {
                char cell = Character.toUpperCase(columns[colNum]);

                Vertex vertex;
                switch(cell) {
                    case 'O': // Normal cell
                        vertex = factory.createVertex(rowNum, colNum);
                        break;
                    case 'S': // Starting cell
                        vertex = factory.createStartVertex(rowNum, colNum);
                        break;
                    case 'F': // Finishing cell
                        vertex = factory.createFinishVertex(rowNum, colNum);
                        break;
                    default: // Everything else is non-traversable
                        continue;
                }

                vertices.put(vertex.getId(), vertex);
            }
        }

        return new GraphData(new Graph(vertices), factory.start.getId(), factory.finish.getId());
    }

    @NotNull
    private Vertex createVertex(int rowNum, int colNum) {
        Vertex vertex = new Vertex(new VertexId(rowNum, colNum));
        vertex.addNeighbors(getNeighborsOf(rowNum, colNum));
        vertexCache.put(vertex.getId(), vertex);
        return vertex;
    }

    @NotNull
    private Vertex createStartVertex(int rowNum, int colNum) {
        if(start != null) {
            throw new IllegalArgumentException("only one starting point is allowed");
        }
        start = createVertex(rowNum, colNum);
        return start;
    }

    @NotNull
    private Vertex createFinishVertex(int rowNum, int colNum) {
        if(finish != null) {
            throw new IllegalArgumentException("only one finishing point is allowed");
        }
        finish = createVertex(rowNum, colNum);
        return finish;
    }

    @NotNull
    private Collection<Vertex> getNeighborsOf(int rowNum, int colNum) {
        ArrayList<Vertex> neighbors = new ArrayList<>(2);

        addNeighbor(neighbors, vertexCache.get(new VertexId(rowNum - 1, colNum)));
        addNeighbor(neighbors, vertexCache.get(new VertexId(rowNum, colNum - 1)));

        return neighbors;
    }

    private static void addNeighbor(@NotNull Collection<Vertex> neighbors,
                                    @Nullable Vertex neighbor) {
        if(neighbor != null) {
            neighbors.add(neighbor);
        }
    }

}
