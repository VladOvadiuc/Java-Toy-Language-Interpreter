package Model.Statements;

import Model.ProgramState;
import File.*;
import Model.Utilities.*;
import Model.Heap.*;

import java.util.ArrayDeque;
import java.util.Deque;

public class forkStmt implements IStmt {

    IStmt statement;

    public forkStmt(IStmt statement) {
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState state) {

        IStack<IStmt> stack2 = new Stack<>();
        IList<Integer> out2 = state.getList();
        IHeap<Integer,Integer> heap2 = state.getHeap();
        IFileTable<Integer,FileData> fileTable2 = state.getFileTable();
        IDictionary<String,Integer> stackSymTable2 = state.getSymTable().copy();

        stack2.add(statement);
        Integer ID = GenIDFork.getID();
        return new ProgramState(stack2,stackSymTable2,out2,fileTable2,heap2,ID);
        //ProgramState(exeStack,dict,list,fileTable,heap,1);

    }

    @Override
    public String toString(){
        return "fork("+statement+")";
    }
}