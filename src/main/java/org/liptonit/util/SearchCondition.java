package org.liptonit.util;

@FunctionalInterface
public interface SearchCondition<T> {
    boolean select(T iterable);
}