package Model.Expressions;

import Model.Heap.IHeap;
import Model.Utilities.IDictionary;
import Exception.*;

public class BooleanExpr implements IExpr {

    private IExpr left, right;
    private String operator;

    public BooleanExpr(String op, IExpr l, IExpr r){
        operator = op;
        left = l;
        right = r;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> dic, IHeap<Integer, Integer> heap) {
        int left = this.left.evaluate(dic,heap);
        int right = this.right.evaluate(dic,heap);
        /*exp1 < exp2
            exp1<=exp2
            exp1==exp2
            exp1!=exp2
            exp1> exp2
            exp1>=exp2*/
        switch (operator){
            case "<":
                if (left<right)
                    return 1;
                return 0;
            case "<=":
                if (left<=right)
                    return 1;
                return 0;
            case "==":
                if (left==right)
                    return 1;
                return 0;
            case "!=":
                if (left!=right)
                    return 1;
                return 0;
            case ">=":
                if (left>=right)
                    return 1;
                return 0;
            case ">":
                if (left>right)
                    return 1;
                return 0;
        }
        throw new InvalidOperator("Invalid operator!");
    }

    public String toString(){
        return "" + left + operator + right;
    }
}
