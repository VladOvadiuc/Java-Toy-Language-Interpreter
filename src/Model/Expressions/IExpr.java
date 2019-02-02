package Model.Expressions;

import Model.Utilities.IDictionary;
import Model.Heap.*;

public interface IExpr {
    int evaluate(IDictionary<String, Integer> dic,IHeap<Integer, Integer> heap);
}
