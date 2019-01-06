package com.shawn.touchstone.functional;

import java.util.function.Function;

/**
 * @author shangfei
 */
public class M<T> implements Monad<T> {

    final private T t;

    private M(T t){
        this.t = t;
    }

    public static <T> M create(T t){
        return new M(t);
    }

    @Override
    public <V> M map(Function<T, V> f) {
        return M.create(f.apply(this.t));
    }

    @Override
    public <V> M flatMap(Function<T, M<V>> f) {
        return f.apply(this.t);
    }

    @Override
    public String toString() {
        return String.format("M( " + t + " )");
    }

}
