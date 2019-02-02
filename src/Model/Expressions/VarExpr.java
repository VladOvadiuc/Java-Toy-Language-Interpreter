package Model.Expressions;

import Model.Heap.IHeap;
import Model.Utilities.IDictionary;

public class VarExpr implements IExpr {
    private String id;

    public VarExpr(String v){
        id=v;
    }
    @Override
    public int evaluate(IDictionary<String,Integer> dic,IHeap<Integer,Integer>heap ){
        return dic.getValue(id);
    }

    public String toString(){
        return id;
    }
}
