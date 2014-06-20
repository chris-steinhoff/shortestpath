package com.clickbank.shortestpath;

import java.io.PrintStream;

public class TerminalColorPrintTarget extends TerminalPrintTarget {

    private static final PrintStream out = System.out;

    private enum Color {
        BLACK("0;30"), RED("0;91"), GREEN("1;92"), YELLOW("0;33"), WHITE("0;37");

        final String number;

        private Color(String number) {
            this.number = number;
        }
    }

    @Override
    public void printObstacle() {
        print('X', Color.RED);
    }

    @Override
    public void printPathVertex(VertexId vertex) {
        print('P', Color.GREEN);
    }

    @Override
    public void printVertex(VertexId vertex) {
        print('O', Color.WHITE);
    }

    @Override
    public void printVisitedVertex(VertexId vertex) {
        print('V', Color.YELLOW);
    }

    @Override
    public void printNewLine() {
        out.println();
    }

    private void print(char c, Color color) {
        out.print((char)27 + "[" + color.number + 'm' + c + (char)27 + "[m");
    }

}
