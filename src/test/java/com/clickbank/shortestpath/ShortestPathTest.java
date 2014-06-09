package com.clickbank.shortestpath;

import com.clickbank.shortestpath.astar.AStarShortestPath;
import com.clickbank.shortestpath.grid.GridGraphFactory;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ShortestPathTest {

    private final Charset utf8 = Charset.forName("UTF-8");
    private final File smallGraphFile = new File("src/test/resources/small.grf");
    private final File concaveGraphFile = new File("src/test/resources/concave.grf");
    private final File wikipediaGraphFile = new File("src/test/resources/wikipedia.grf");

    @Test
    public void testGraphFile() throws Exception {
        assertTrue(smallGraphFile.exists());
        List<String> data = Files.readAllLines(smallGraphFile.toPath(), utf8);
        for (String s : data) {
            System.out.println(s);
        }
    }

    @Test
    public void testGraphFactory() throws Exception {
        GraphData data = GridGraphFactory.createGraph(smallGraphFile.getAbsolutePath());
        assertNotNull(data);
        assertNotNull(data.graph);
        assertNotNull(data.start);
        assertNotNull(data.finish);

        assertEquals(new VertexId(2, 0), data.start);
        assertEquals(new VertexId(2, 4), data.finish);
    }

    @Test
    public void testAStar() throws Exception {
        GraphData data = GridGraphFactory.createGraph(concaveGraphFile.getAbsolutePath());
        AStarShortestPath aStar = new AStarShortestPath(data.graph, new ManhattanHeuristic());
        GraphPath path = aStar.findPath(data.start, data.finish);
        assertNotNull(path);

        Iterator<VertexId> it = path.iterator();
        assertNotNull(it);
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    @Test
    public void testGraphPrint() throws Exception {
        GraphData data = GridGraphFactory.createGraph(wikipediaGraphFile.getAbsolutePath());
        //data.graph.printToTerminal();
        data.graph.printShortestPathToTerminal(data.start, data.finish);
    }

}
