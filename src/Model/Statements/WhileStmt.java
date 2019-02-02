package Model.Statements;

import Model.ProgramState;
import Model.Expressions.IExpr;
import Model.Utilities.IDictionary;
import Model.Utilities.IStack;
import Model.Heap.IHeap;

public class WhileStmt implements IStmt {
    IExpr exp;
    IStmt statement;

    public WhileStmt(IExpr exp, IStmt statement) {
        this.exp = exp;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState state) {

        IStack<IStmt> stack = state.getStack();
        IHeap<Integer, Integer> heap = state.getHeap();
        IDictionary<String, Integer> dict = state.getSymTable();

        int val = exp.evaluate(dict, heap);

        if(val != 0) {
            stack.add(new WhileStmt(exp, statement));
            stack.add(statement);
        }

        return null;
    }

    @Override
    public String toString(){
        return "While("+exp+"){"+statement+"}";
    }
}