package File;

import Exception.FileException;
import Model.ProgramState;
import Model.Expressions.IExpr;
import Model.Statements.IStmt;
import Model.Utilities.IDictionary;
import Model.Heap.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {

    IExpr exp;
    String varName;

    public ReadFile(IExpr exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }


    @Override
    public ProgramState execute(ProgramState state) {

        IDictionary<String, Integer> dict = state.getSymTable();
        IFileTable<Integer, FileData> fileTable = state.getFileTable();

        IHeap<Integer, Integer> heap = state.getHeap();
        int id = exp.evaluate(dict,heap);



        if(!fileTable.contains(id))
            throw new FileException("This ID is not in FileTable");

        BufferedReader reader = fileTable.get(id).getReader();

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new FileException("Can't read line");
        }

        int value = 0;
        if(line != null)
            value = Integer.parseInt(line);

        dict.setValue(varName, value);

        return null;
    }

    @Override
    public String toString(){
        return "read("+varName + ", " + exp+")";
    }

}