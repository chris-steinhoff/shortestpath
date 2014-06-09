package com.clickbank.shortestpath;

import java.io.PrintStream;

public class TerminalPrintTarget implements PrintTarget {

    private static final PrintStream out = System.out;

    @Override
    public void printObstacle() {
        out.print('X');
    }

    @Override
    public void printPathVertex(VertexId vertex) {
        out.print('P');
    }

    @Override
    public void printVertex(VertexId vertex) {
        out.print('O');
    }

    @Override
    public void printVisitedVertex(VertexId vertex) {
        out.print('V');
    }

    @Override
    public void printNewLine() {
        out.println();
    }

}
