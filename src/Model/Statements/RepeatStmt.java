package Model.Statements;


import Model.Expressions.IExpr;
import Model.ProgramState;


public class RepeatStmt implements IStmt {
    private IStmt stmt;
    private IExpr exp;
    public RepeatStmt(IStmt stmt, IExpr exp) {
        this.stmt = stmt;
        this.exp = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) {
        IStmt act = new CompStmt(stmt, new WhileStmt(new NotExp(this.exp), stmt));
        state.getStack().add(act);
        return null;
    }

    @Override
    public String toString() {
        return "repeat " + this.stmt.toString() + " until " + this.exp.toString();
    }
}