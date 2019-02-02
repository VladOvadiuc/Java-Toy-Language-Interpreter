package Model.Statements;


import Model.Expressions.IExpr;
import Model.ProgramState;

public class forStmt implements IStmt {

    IStmt s1,s3,s4;
    IExpr s2;

    public forStmt(IStmt s1,  IExpr s2, IStmt s3, IStmt s4) {

        this.s1 = s1;
        this.s3 = s3;
        this.s4 = s4;
        this.s2 = s2;
    }



    @Override
    public ProgramState execute(ProgramState state) {

        IStmt a1 = new WhileStmt(s2,new CompStmt(s4,s3));
        IStmt a2 = new CompStmt(s1, a1);

        state.getStack().add(a2);
        return null;
    }

    @Override
    public String toString(){
        return "for(" + s1 + ";" + s2 + ";" + s3 + ")"+ "{" + s4 + "}";
    }
}