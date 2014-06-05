package com.clickbank.shortestpath;

public class GraphGenerator {

    public static void main(String[] args) {
        char[] alphabet = {'X', 'O', 'O'};
        int rows = 100, columns = 100;

        for(int r = 0 ; r < rows ; ++r) {
            for(int c = 0 ; c < columns ; ++c) {
                char vertex = alphabet[(int)(Math.random() * alphabet.length)];
                System.out.print(vertex);
            }
            System.out.println();
        }

    }

}
