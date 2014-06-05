package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AStarShortestPath {

    public Iterable<Vertex> getPath(Vertex start, Vertex finish) {
        HashSet<Vertex> closedSet = new HashSet<>();
        PriorityQueue<AStarVertex> openSet = new PriorityQueue<>(11, new AStarComparator(finish));

        AStarVertex s = new AStarVertex(start);
        openSet.add(s);

        s.gScore = 0;
        s.fScore = s.gScore + s.estimatedDistanceTo(finish);

        while(!openSet.isEmpty()) {
            AStarVertex current = openSet.poll();
            if(current.equals(finish)) {
                return new Path(current);
            }

            closedSet.add(current);
            for(Vertex vertex : current.getNeighbors()) {
                AStarVertex neighbor = new AStarVertex(vertex);
                if(closedSet.contains(vertex)) {
                    continue;
                }
                double tentativeGScore = current.gScore + 1;

                if(!openSet.contains(neighbor) || tentativeGScore < neighbor.gScore) {
                    neighbor.previousVertex = current;
                    neighbor.gScore = tentativeGScore;
                    neighbor.fScore = neighbor.gScore + neighbor.estimatedDistanceTo(finish);
                    if(!openSet.contains(neighbor)) {
                        openSet.offer(neighbor);
                    }
                }
            }
        }

        return null;
    }

    class AStarComparator implements Comparator<Vertex> {

        public final Vertex finish;

        AStarComparator(Vertex finish) {
            this.finish = finish;
        }

        @Override
        public int compare(Vertex o1, Vertex o2) {
            long d1 = Math.round(o1.estimatedDistanceTo(finish) * 100.0);
            long d2 = Math.round(o2.estimatedDistanceTo(finish) * 100.0);
            return (int)(d1 - d2);
        }

    }

    class AStarVertex extends Vertex {

        public double gScore;
        public double fScore;
        public AStarVertex previousVertex;

        public AStarVertex(@NotNull Vertex vertex) {
            super(vertex.getId());
            this.addNeighbors(vertex.getNeighbors());
        }

    }

    class Path implements Iterable<Vertex> {

        private final LinkedList<Vertex> path;

        Path(AStarVertex finish) {
            path = new LinkedList<>();
            path.add(finish);
            AStarVertex prev = finish;
            while((prev = prev.previousVertex) != null) {
                path.add(prev);
            }
        }

        @Override
        public Iterator<Vertex> iterator() {
            return path.descendingIterator();
        }

    }

}
