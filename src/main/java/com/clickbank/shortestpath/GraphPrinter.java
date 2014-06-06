package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

public interface GraphPrinter {

    void printGraph(@NotNull Graph graph, @NotNull Object target);

}
