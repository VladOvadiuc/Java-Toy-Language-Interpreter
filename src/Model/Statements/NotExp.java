package Model.Statements;



import Exception.DivisionByZero;
import Model.Expressions.IExpr;
import Model.Heap.IHeap;
import Model.Utilities.IDictionary;


public class NotExp implements IExpr {
    private IExpr exp;
    public NotExp(IExpr exp) {
        this.exp = exp;
    }
    @Override
    public int evaluate(IDictionary<String, Integer> symTable, IHeap<Integer,Integer> heap) {
        // if x evals to 0 => false => !false = true
        /// else !true = false
        int x = this.exp.evaluate(symTable, heap);
        if(x == 0)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "!" + this.exp.toString();
    }
}