package app.utils;

@FunctionalInterface
public interface TriFunction<A, B, C, Class> {
    Class apply(A a, B b, C c);
}
