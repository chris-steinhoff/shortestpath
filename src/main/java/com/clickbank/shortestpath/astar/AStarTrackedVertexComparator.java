package com.clickbank.shortestpath.astar;

import com.clickbank.shortestpath.TrackedVertex;

import java.util.Comparator;

public class AStarTrackedVertexComparator implements Comparator<TrackedVertex> {

    @Override
    public int compare(TrackedVertex o1, TrackedVertex o2) {
        long d1 = Math.round(o1.getHeuristicDistance() * 100.0);
        long d2 = Math.round(o2.getHeuristicDistance() * 100.0);
        int d = (int)(d1 - d2);
        if(d == 0) {
            // Use the traveled distance to break the tie
            d1 = Math.round(o1.getTraveledDistance() * 100.0);
            d2 = Math.round(o2.getTraveledDistance() * 100.0);
            d = (int)(d1 - d2);
        }
        return d;
    }

}
