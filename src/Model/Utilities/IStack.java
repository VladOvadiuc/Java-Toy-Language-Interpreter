package Model.Utilities;

public interface IStack<T> {
    void add(T x);
    Iterable<T> getElements();
    boolean isEmpty();
    T top();
    T pop();
}
