package com.clickbank.shortestpath;

import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ShortestPathTest {

    private final Charset utf8 = Charset.forName("UTF-8");
    private final File graphFile = new File("src/test/resources/simple.grf");

    @Test
    public void testGraphFile() throws Exception {
        assertTrue(graphFile.exists());
        List<String> data = Files.readAllLines(graphFile.toPath(), utf8);
        for (String s : data) {
            System.out.println(s);
        }
    }

    @Test
    public void testGraphFactory() throws Exception {
        GraphData data = GraphFactory.createGraph(graphFile.getAbsolutePath());
        assertNotNull(data);
        assertNotNull(data.graph);
        assertNotNull(data.start);
        assertNotNull(data.finish);

        assertEquals(new Vertex(new VertexId(2, 0)), data.start);
        assertEquals(new Vertex(new VertexId(2, 4)), data.finish);
    }

    @Test
    public void testAStar() throws Exception {
        GraphData data = GraphFactory.createGraph(graphFile.getAbsolutePath());
        AStarShortestPath aStar = new AStarShortestPath();
        Iterable<Vertex> path = aStar.getPath(data.start, data.finish);
        assertNotNull(path);

        Iterator<Vertex> it = path.iterator();
        assertNotNull(it);
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
