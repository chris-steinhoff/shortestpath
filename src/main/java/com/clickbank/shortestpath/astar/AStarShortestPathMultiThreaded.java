package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.Graph;
import com.clickbank.shortestpath.GraphPath;
import com.clickbank.shortestpath.Heuristic;
import com.clickbank.shortestpath.PathFinder;
import com.clickbank.shortestpath.TrackedVertex;
import com.clickbank.shortestpath.TrackedVertexFactory;
import com.clickbank.shortestpath.VertexId;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AStarShortestPathMultiThreaded extends AStarShortestPath {

    private final ExecutorService threadPool;

    public AStarShortestPathMultiThreaded(@NotNull Graph graph, @NotNull Heuristic heuristic,
                                          @NotNull ExecutorService threadPool) {
        super(graph, heuristic);
        this.threadPool = threadPool;
    }

    @Override
    @NotNull
    public GraphPath findPath(@NotNull VertexId start, @NotNull VertexId finish) {
        AStarTrackedVertexFactory trackedVertexFactory = new AStarTrackedVertexFactory(heuristic, finish);
        AStarContext builder = this.context;
        CountDownLatch latch;

        builder.open(trackedVertexFactory.createTrackedVertex(start));
        while(builder.hasNext()) {
            TrackedVertex current = builder.closeNext();
            if(current.getId().equals(finish)) {
                return builder.getGraphPath(current);
            }

            Set<VertexId> neighbors = graph.getNeighbors(current.getId());
            latch = new CountDownLatch(neighbors.size());
            for(VertexId vertex : neighbors) {
                threadPool.submit(new NeighborProcessor(builder, trackedVertexFactory, current, vertex, latch));
            }
            try {
                latch.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
                return builder.getFailedGraphPath();
            }
        }

        return builder.getFailedGraphPath();
    }

    class NeighborProcessor implements Runnable {

        private final AStarContext context;
        private final TrackedVertexFactory trackedVertexFactory;
        private final TrackedVertex current;
        private final VertexId vertex;
        private final CountDownLatch latch;

        NeighborProcessor(AStarContext context, TrackedVertexFactory trackedVertexFactory, TrackedVertex current, VertexId vertex, CountDownLatch latch) {
            this.context = context;
            this.trackedVertexFactory = trackedVertexFactory;
            this.current = current;
            this.vertex = vertex;
            this.latch = latch;
        }

        @Override
        public void run() {
            TrackedVertex neighbor = trackedVertexFactory.createNeighboringTrackedVertex(current, vertex);
            context.openIfNotClosed(neighbor);
            latch.countDown();
        }

    }

}
