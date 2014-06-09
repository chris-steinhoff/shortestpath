package com.clickbank.shortestpath;

import com.clickbank.shortestpath.astar.AStarContext;
import com.clickbank.shortestpath.astar.AStarContextSkipList;
import com.clickbank.shortestpath.astar.AStarShortestPath;
import com.clickbank.shortestpath.astar.AStarShortestPathMultiThreaded;
import com.clickbank.shortestpath.grid.GridGraphFactory;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

import java.io.File;

public class AStarContextBenchmark extends Benchmark {

    private final File concaveGraphFile = new File("src/test/resources/concave.grf");

    @Param({"SINGLE_THREADED", "MULTI_THREADED"})
    public ShortestPathImpl shortestPathImpl;// = ShortestPathImpl.SINGLE_THREADED;

    @Param({"PRIORITY_QUEUE", "SKIP_LIST"})
    public ShortestPathContextImpl shortestPathContextImpl;

    @Param({"MANHATTAN", "EUCLIDEAN"})
    public HeuristicImpl heuristicImpl;

    private GraphData graphData;
    private AStarShortestPath shortestPath;
//    private AStarShortestPathSkipList shortestPathSkipList;
    //private GraphPath graphPath;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        graphData = GridGraphFactory.createGraph(concaveGraphFile.getAbsolutePath());
        shortestPath = shortestPathImpl.getImpl(graphData.graph, heuristicImpl.getImpl());
        shortestPath.setContext(shortestPathContextImpl.getImpl());
//        shortestPath = new AStarShortestPath(graphData.graph, new ManhattanHeuristic());
//        shortestPathSkipList = new AStarShortestPathSkipList(graphData.graph, new ManhattanHeuristic());
    }

    /*@Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if(graphPath != null) {
            GraphPathPrinter printer = new GridGraphPrinter(graphData.graph);
            printer.printGraphPath(graphPath, new TerminalPrintTarget());
        }
    }*/

    /*@Macrobenchmark
    public GraphPath timeShortestPath() {
        return shortestPath.findPath(graphData.start, graphData.finish);
    }*/

    public GraphPath timeShortestPath(int reps) {
        AStarShortestPath shortestPath = this.shortestPath;
        VertexId start = graphData.start, finish = graphData.finish;
        GraphPath path = null;
        for(int i = 0 ; i < reps ; ++i) {
            path = shortestPath.findPath(start, finish);
        }
        //graphPath = path;
        return path;
    }

    /*@Override
    protected void setUp() throws Exception {
        int n = 100;
        vertices = new ArrayList<>(n);
        for(int w = 0 ; w < n ; ++w) {
            for(int h = 0 ; h < n ; h++) {
                vertices.add(new TrackedVertex(new VertexId(w, h), 0, 0, null));
            }
        }
    }

    public TrackedVertex timePriorityQueue(int reps) {
        ArrayList<TrackedVertex> vertices = this.vertices;
        PriorityQueue<TrackedVertex> queue = this.queue;
        int listSize = vertices.size();
        TrackedVertex vertex, polled = null;
        for(int i = 0 ; i < reps ; ++i) {
            if((i % 3) == 0) {
                polled = queue.poll();
            } else {
                vertex = vertices.get(i % listSize);
                if(!queue.contains(vertex)) {
                    queue.offer(vertex);
                }
            }
        }
        return polled;
    }

    public TrackedVertex timeSkipList(int reps) {
        ArrayList<TrackedVertex> vertices = this.vertices;
        ConcurrentSkipListSet<TrackedVertex> skipList = this.skipList;
        int listSize = vertices.size();
        TrackedVertex vertex, polled = null;
        for(int i = 0 ; i < reps ; ++i) {
            if((i % 3) == 0) {
                polled = skipList.pollFirst();
            } else {
                vertex = vertices.get(i % listSize);
                if(!skipList.contains(vertex)) {
                    skipList.add(vertex);
                }
            }
        }
        return polled;
    }*/

    public enum ShortestPathImpl {
        SINGLE_THREADED, MULTI_THREADED;

        public AStarShortestPath getImpl(Graph graph, Heuristic heuristic) {
            AStarShortestPath impl = null;
            switch(this) {
                case SINGLE_THREADED:
                    impl = new AStarShortestPath(graph, heuristic);
                    break;
                case MULTI_THREADED:
                    impl = new AStarShortestPathMultiThreaded(graph, heuristic);
                    break;
            }
            return impl;
        }
    }

    public enum ShortestPathContextImpl {
        PRIORITY_QUEUE(new AStarContext()), SKIP_LIST(new AStarContextSkipList());

        private final AStarContext impl;

        ShortestPathContextImpl(AStarContext impl) {
            this.impl = impl;
        }

        public AStarContext getImpl() {
            return impl;
        }
    }

    public enum HeuristicImpl {
        MANHATTAN(new ManhattanHeuristic()), EUCLIDEAN(new EuclideanHeuristic());

        private final Heuristic impl;

        HeuristicImpl(Heuristic impl) {
            this.impl = impl;
        }

        public Heuristic getImpl() {
            return impl;
        }
    }

    public static void main(String[] args) {
        CaliperMain.main(AStarContextBenchmark.class, args);
    }

}
