package com.clickbank.shortestpath;

import org.jetbrains.annotations.NotNull;

public interface GraphPathPrinter {

    void printGraphPath(@NotNull GraphPath path, @NotNull PrintTarget target);

}
