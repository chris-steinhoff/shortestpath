package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

public interface PathFinder {

    @NotNull
    GraphPath findPath(@NotNull VertexId start, @NotNull VertexId finish);

}
