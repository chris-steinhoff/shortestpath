package com.clickbank.shortestpath.astar;

import java.util.Comparator;

public class AStarPriorityQueueComparator implements Comparator<AStarVertex> {

    @Override
    public int compare(AStarVertex o1, AStarVertex o2) {
        long d1 = Math.round(o1.getHeuristicDistance() * 100.0);
        long d2 = Math.round(o2.getHeuristicDistance() * 100.0);
        return (int)(d1 - d2);
    }

}
