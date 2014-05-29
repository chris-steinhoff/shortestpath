package com.clickbank.shortestpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] params) throws Exception {
        Arguments args = parseArguments(params);
        List<List<Character>> list = readFile(args.filename);
        for(List<Character> characters : list) {
            for(Character character : characters) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private static Arguments parseArguments(String[] args) {
        Arguments arguments = new Arguments();
        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            if("-f".equals(arg)) {
                if(++i == args.length) {
                    throw new IllegalArgumentException();
                }
                arguments.filename = args[i];
            }
        }
        return arguments;
    }

    private static List<List<Character>> readFile(String filename) throws IOException {
        File file = new File(filename);

        if(file.length() == 0) {
            throw new IllegalArgumentException("graph file is empty");
        }

        LinkedList<List<Character>> data = new LinkedList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file), (int)file.length())) {
            String line;
            while((line = reader.readLine()) != null) {
                LinkedList<Character> row = new LinkedList<>();
                for(char c : line.toCharArray()) {
                    row.add(Character.toUpperCase(c));
                }
                data.add(row);
            }
        }

        return data;
    }

    static class Arguments {
        public String filename;
    }
}
