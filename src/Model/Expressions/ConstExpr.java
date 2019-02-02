package Model.Expressions;

import Model.Heap.IHeap;
import Model.Utilities.IDictionary;

public class ConstExpr implements IExpr {
    private int number;

    public ConstExpr(int nr) {
        number = nr;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> dic, IHeap<Integer, Integer> heap) {
        return number;
    }

    public String toString() {
        return "" + number;
    }
}
