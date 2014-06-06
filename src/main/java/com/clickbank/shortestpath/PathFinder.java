package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

public interface PathFinder {

    Iterable<TrackedVertex> findPath(@NotNull Vertex start, @NotNull Vertex finish);

}
