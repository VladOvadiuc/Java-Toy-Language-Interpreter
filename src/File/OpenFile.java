package File;

import Exception.FileException;
import Model.ProgramState;
import Model.Statements.IStmt;
import Model.Utilities.IDictionary;

import java.io.*;


public class OpenFile implements IStmt {

    private String fileName;
    private String varName;

    public OpenFile(String varName, String fileName) {
        this.fileName = fileName;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        IFileTable<Integer, FileData> fileTable = state.getFileTable();
        IDictionary<String,Integer> dic=state.getSymTable();
        BufferedReader reader;

        for(FileData d: fileTable.getAllValues()) {
            if (d.getFileName().equals(fileName))
                throw new FileException("The file is already opened in FileTable");
        }

        try{
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            throw new FileException("File not found");
        }

        int id = Generator.gen_ID();

        fileTable.add(id, new FileData(fileName, reader));
        dic.setValue(varName,id);
        return null;
    }

    @Override
    public String toString(){
        return "open("+varName+", "+fileName+')';
    }

}