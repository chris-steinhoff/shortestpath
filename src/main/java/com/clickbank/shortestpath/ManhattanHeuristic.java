package com.clickbank.shortestpath;

public class ManhattanHeuristic implements Heuristic {

    @Override
    public double distanceBetween(Coordinate a, Coordinate b) {
        double d1 = Math.abs(a.getX() - b.getX());
        double d2 = Math.abs(a.getY() - b.getY());
        return (d1 + d2);
    }

}
