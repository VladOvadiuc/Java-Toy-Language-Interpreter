package Repository;

import Model.Heap.IHeap;
import Model.ProgramState;

import java.util.ArrayList;
import java.util.List;
import Exception.FileException;
import Model.ProgramState;
import File.FileData;
import File.IFileTable;
import Model.Statements.IStmt;
import Model.Utilities.*;

import java.io.*;

public class Repository implements IRepo {
    private List<ProgramState> listPrg = new ArrayList<>();
    private int current = 0;

    @Override
    public void addPrg(ProgramState prg) {
        listPrg.add(prg);
    }

    public ProgramState getCurrentPrg() {
        return listPrg.get(current);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (ProgramState state : listPrg) {
            sb.append(state).append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<ProgramState> getPrgList() {
        return listPrg;
    }

    @Override
    public void setPrgList(List<ProgramState> list) {
        this.listPrg = list;
    }
    @Override
    public void logPrgStateExec(ProgramState state) {


        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter("text2.txt", true)))){

            IStack<IStmt> stack = state.getStack();
            IDictionary<String, Integer> dict = state.getSymTable();
            IList<Integer> out = state.getList();
            IFileTable<Integer, FileData> fileTable = state.getFileTable();

            IHeap<Integer,Integer> heap = state.getHeap();

            logFile.println("ExeStack: " + state.getID());
            for(IStmt S: stack.getElements())
                logFile.println(""+ S);


            logFile.println("\nSymTable:\n");
            for(String key: dict.getElements())
                logFile.println(key + "-->" + dict.getValue(key) +'\n');

            logFile.println("\nOut:\n");
            for(Integer i: out.getElements())
                logFile.println(""+i+"\n" );


            logFile.println("\nFileTable:\n");
            for(Integer i: fileTable.getAllKeys())
                logFile.println("" + i + "-->" + fileTable.get(i).getFileName() + "\n");

            logFile.println("\nHeap:\n");
            for(Integer key: heap.getAll())
               logFile.println("" +key +"->"+ heap.get(key) + "\n");

            logFile.println("\n");

        } catch (FileNotFoundException e) {
            throw new FileException("File not found in PrintWriter");
        } catch (IOException e) {
            throw new FileException("IO exception at PrintWriter");
        }

    }
}
