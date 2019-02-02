package Model.Heap;

import Model.ProgramState;
import Model.Expressions.IExpr;
import Model.Statements.IStmt;
import Model.Utilities.IDictionary;

public class HeapAlloc implements IStmt {

    String varName;
    IExpr exp;

    public HeapAlloc(String varName, IExpr exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        IDictionary<String, Integer> dict = state.getSymTable();
        IHeap<Integer, Integer> heap = state.getHeap();

        int id = GenIDHeap.getID();
        int val = exp.evaluate(dict, heap);

        heap.add(id, val);
        if(dict.exists(varName))
            dict.setValue(varName, id);
        else
            dict.setValue(varName, id);


        return null;
    }

    @Override
    public String toString(){
        return "NewH(" + varName + ", " + exp + ")";
    }
}