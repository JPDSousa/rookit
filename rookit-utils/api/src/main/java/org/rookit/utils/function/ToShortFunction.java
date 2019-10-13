package org.rookit.utils.function;

@FunctionalInterface
public interface ToShortFunction<T> {

    short apply(T input);

}
