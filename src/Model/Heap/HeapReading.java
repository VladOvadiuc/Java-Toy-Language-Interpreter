package Model.Heap;

import Exception.VariableNotDefined;
import Model.ProgramState;
import Model.Expressions.IExpr;
import Model.Statements.IStmt;
import Model.Utilities.IDictionary;

public class HeapReading implements IExpr {

    String varName;

    public HeapReading(String varName) {
        this.varName = varName;
    }


    @Override
    public int evaluate(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap) {

        if(!dict.exists(varName)) {

            throw new VariableNotDefined("This key is not in our symTable");
        }
        int heapID = dict.getValue(varName);

        if(!heap.contains(heapID)) {

            throw new VariableNotDefined("This key is not in our heapTable");
        }

        return  heap.get(heapID);
    }

    @Override
    public String toString(){
        return "heapReading(" + varName +")";
    }

}