package Model.Utilities;

public interface IList<T> {
    void add(T x);

    Iterable<T> getElements();

    void remove(T x);
}
