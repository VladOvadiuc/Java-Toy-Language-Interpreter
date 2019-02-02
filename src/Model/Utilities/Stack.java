package Model.Utilities;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<T> implements IStack<T> {
    private Deque<T> elements=new ArrayDeque<T>();

    @Override
    public void add(T e){
        elements.push(e);
    }
    public Iterable<T> getElements(){
        return elements;
    }
    public boolean isEmpty(){
        return elements.isEmpty();
    }
    public T top(){
        return elements.peekFirst();
    }
    public T pop(){
        return elements.pop();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(T i : elements){
            sb.append(i).append(" ");
        }
        return sb.toString();
    }
}
