package com.clickbank.shortestpath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public abstract class GraphFactory {

    private static final Charset utf8 = Charset.forName("UTF-8");

    public static GraphData createGraph(String filename) throws IOException {
        File file = new File(filename);

        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        if(file.length() == 0) {
            throw new IllegalArgumentException("file is empty");
        }

        List<String> contents = Files.readAllLines(file.toPath(), utf8);

        HashMap<String,Vertex> vertexCache = new HashMap<>();

        Graph graph = new Graph();
        Vertex start = null, finish = null;
        int row = 0;
        for(String line : contents) {
            int column = 0;
            for(char c : line.toCharArray()) {
                Vertex vertex = new Vertex(row, column);
                c = Character.toUpperCase(c);
                switch(c) {
                    case 'S':
                        start = vertex;
                        break;
                    case 'F':
                        finish = vertex;
                        break;
                }
                graph.addVertex(vertex);
                ++column;
            }
            ++row;
        }

        return new GraphData(new Graph(), start, finish);
    }

}
