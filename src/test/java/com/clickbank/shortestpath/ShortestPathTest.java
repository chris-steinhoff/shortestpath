package com.clickbank.shortestpath;

import com.clickbank.shortestpath.astar.AStarShortestPath;
import com.clickbank.shortestpath.grid.GridGraphFactory;
import com.clickbank.shortestpath.grid.GridGraphPrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ShortestPathTest {

    private static final String TERM = System.getenv("TERM");

    @Parameters
    public static Collection<Object[]> graphFiles() {
        return Arrays.asList(
                new Object[] {new File("src/test/resources/small.grf")},
                new Object[] {new File("src/test/resources/medium.grf")},
                new Object[] {new File("src/test/resources/large.grf")},
                new Object[] {new File("src/test/resources/concave.grf")},
                new Object[] {new File("src/test/resources/wikipedia.grf")},
                new Object[] {new File("src/test/resources/bench.grf")}
        );
    }


    private final File graphFile;

    public ShortestPathTest(File graphFile) {
        this.graphFile = graphFile;
    }

    @Test
    public void printShortestPath() throws Exception {
        System.out.println("graphFile = " + graphFile.getName());
        GraphData data = GridGraphFactory.createGraph(graphFile.getAbsolutePath());

        ManhattanHeuristic heuristic = new ManhattanHeuristic();
        AStarShortestPath shortestPath = new AStarShortestPath(data.graph, heuristic);
        GraphPath path = shortestPath.findPath(data.start, data.finish);

        TerminalPrintTarget target;
        if("linux".equals(TERM)) {
            target = new TerminalPrintTarget();
        } else {
            target = new TerminalColorPrintTarget();
        }
        GridGraphPrinter printer = new GridGraphPrinter(data.graph);
        printer.printGraphPath(path, target);
        System.out.println();
    }

}
