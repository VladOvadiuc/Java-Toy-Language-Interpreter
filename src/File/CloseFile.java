package File;

import Exception.FileException;
import Model.Heap.IHeap;
import Model.ProgramState;
import Model.Expressions.IExpr;
import Model.Statements.IStmt;
import Model.Utilities.IDictionary;

import java.io.BufferedReader;
import java.io.IOException;


public class CloseFile implements IStmt {

    private IExpr exp;

    public CloseFile(IExpr exp){
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        IDictionary<String, Integer> dict = state.getSymTable();
        IFileTable<Integer, FileData> fileTable = state.getFileTable();

        IHeap<Integer, Integer> heap = state.getHeap();
        int id = exp.evaluate(dict,heap);


        if(!fileTable.contains(id))
            throw new FileException("Can't close the file because don't exist in FileTable");

        FileData fileData = fileTable.get(id);
        BufferedReader bufferedReader = fileData.getReader();

        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new FileException("Can't close the file");
        }

        fileTable.delete(id);
        //dict.delete();

        return null;
    }

    @Override
    public String toString(){
        return "close("+exp+")";
    }


}