package com.clickbank.shortestpath;

import java.util.Iterator;
import java.util.LinkedList;

public class GraphPath implements Iterable<TrackedVertex> {

    private final LinkedList<TrackedVertex> path;

    public GraphPath(TrackedVertex finish) {
        path = new LinkedList<>();
        path.add(finish);
        TrackedVertex prev = finish;
        while((prev = prev.getPreviousVertex()) != null) {
            path.add(prev);
        }
    }

    @Override
    public Iterator<TrackedVertex> iterator() {
        return path.descendingIterator();
    }

}
