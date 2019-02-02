package Model.Statements;

import Model.Expressions.IExpr;
import Model.Heap.IHeap;
import Model.ProgramState;
import Model.Utilities.IDictionary;
import Model.Utilities.IStack;

public class SwitchStmt implements IStmt {

    private IExpr condition;
    private IExpr case1;
    private IStmt stmt1;
    private IExpr case2;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(IExpr condition, IExpr case1, IStmt stmt1, IExpr case2, IStmt stmt2, IStmt stmt3){
        this.condition = condition;
        this.case1 = case1;
        this.case2 = case2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public ProgramState execute(ProgramState state)  {
        IStack<IStmt> Stack = state.getStack();
        IDictionary<String, Integer> symTable = state.getSymTable();
        IHeap<Integer, Integer> heap = state.getHeap();

        Integer exp = condition.evaluate(symTable, heap);
        Integer exp1 = case1.evaluate(symTable, heap);
        Integer exp2 = case2.evaluate(symTable, heap);


        IStmt newStmt;
        if(exp.equals(exp1))
            newStmt = stmt1;
        else if(exp.equals(exp2))
            newStmt = stmt2;
        else
            newStmt = stmt3;
        Stack.add(newStmt);
        state.setStack(Stack);
        return null;

    }
    @Override
    public String toString(){
        return "switch( " + condition.toString() + ") \n(case( " + case1.toString() + " ) " + stmt1.toString() + ")\n(case( " + case2.toString() + " ) " + stmt2.toString() + ")\n(default " + stmt3.toString() + " )";
    }

}
