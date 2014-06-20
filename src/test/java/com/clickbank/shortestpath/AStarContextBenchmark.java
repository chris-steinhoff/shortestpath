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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AStarContextBenchmark extends Benchmark {

    private final File concaveGraphFile = new File("src/test/resources/bench.grf");

    @Param({"MULTI_THREADED", "SINGLE_THREADED"})
    public ShortestPathImpl shortestPathImpl;

    @Param({"PRIORITY_QUEUE", "SKIP_LIST"})
    public ShortestPathContextImpl shortestPathContextImpl;

    @Param({"MANHATTAN", "EUCLIDEAN"})
    public HeuristicImpl heuristicImpl;

    private ExecutorService threadPool;
    private GraphData graphData;
    private AStarShortestPath shortestPath;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //threadPool = Executors.newCachedThreadPool();
        ThreadPoolExecutor tp = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4));
        tp.prestartAllCoreThreads();
        threadPool = tp;

        graphData = GridGraphFactory.createGraph(concaveGraphFile.getAbsolutePath());
        shortestPath = shortestPathImpl.getImpl(graphData.graph, heuristicImpl.getImpl(), threadPool);
        shortestPath.setContext(shortestPathContextImpl.getImpl());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        threadPool.shutdownNow();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }

    public GraphPath timeShortestPath(int reps) {
        AStarShortestPath shortestPath = this.shortestPath;
        VertexId start = graphData.start, finish = graphData.finish;
        GraphPath path = null;
        for(int i = 0 ; i < reps ; ++i) {
            path = shortestPath.findPath(start, finish);
        }
        return path;
    }

    public enum ShortestPathImpl {
        SINGLE_THREADED, MULTI_THREADED;

        public AStarShortestPath getImpl(Graph graph, Heuristic heuristic, ExecutorService threadPool) {
            AStarShortestPath impl = null;
            switch(this) {
                case SINGLE_THREADED:
                    impl = new AStarShortestPath(graph, heuristic);
                    break;
                case MULTI_THREADED:
                    impl = new AStarShortestPathMultiThreaded(graph, heuristic, threadPool);
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
