package com.clickbank.shortestpath;

import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

@Immutable
public class VertexId implements Comparable<VertexId> {

    public final int x;
    public final int y;

    public VertexId(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof VertexId)) return false;

        VertexId vertexId = (VertexId)o;

        return ((x == vertexId.x) && (y == vertexId.y));
    }

    @Override
    public int hashCode() {
        int hash = 5381;
        hash = ((hash << 5) + hash) ^ x;
        hash = ((hash << 5) + hash) ^ y;
        return hash;
    }

    @Override
    public int compareTo(@NotNull VertexId o) {
        int comp = x - o.x;
        if(comp == 0) {
            comp = y - o.y;
        }
        return comp;
    }

    @Override
    public String toString() {
        return String.format("{%d,%d}", x, y);
    }

}
