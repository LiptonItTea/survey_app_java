package org.liptonit.util;

import org.liptonit.entity.DBEntity;

@FunctionalInterface
public interface Patcher<T extends DBEntity> {
    void patch(T entity);
}
