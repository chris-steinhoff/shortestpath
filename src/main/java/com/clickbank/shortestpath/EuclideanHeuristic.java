package com.clickbank.shortestpath;

public class EuclideanHeuristic implements Heuristic {

    @Override
    public double distanceBetween(Coordinate a, Coordinate b) {
        double d1 = a.getX() - b.getX();
        double d2 = a.getY() - b.getY();
        double aSquared = d1 * d1;
        double bSquared = d2 * d2;
        return Math.sqrt(aSquared + bSquared);

    }

}
