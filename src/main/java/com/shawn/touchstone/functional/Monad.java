package com.shawn.touchstone.functional;

import java.util.function.Function;

/**
 * @author shangfei
 */
public interface Monad<T> {

    <V> M map(Function<T, V> R);

    <V> M flatMap(Function<T, M<V>> f);
}
