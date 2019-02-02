package Model.Heap;

import Model.Statements.IStmt;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Utilities.*;
import Exception.*;

public class HeapWriting implements IStmt {

    String varName;
    IExpr exp;

    public HeapWriting(String varName, IExpr exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Integer> dict = state.getSymTable();
        IHeap<Integer, Integer> heap = state.getHeap();

        if(!dict.exists(varName))
            throw new VariableNotDefined("This key is not in our symTable");
        int heapID = dict.getValue(varName);

        if(!heap.contains(heapID))
            throw new VariableNotDefined("This key is not in our heapTable");

        int val = exp.evaluate(dict, heap);
        heap.update(heapID, val);

        return null;
    }

    @Override
    public String toString(){
        return "heapWriting("+varName+","+exp+")";
    }
}