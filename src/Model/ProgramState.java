package Model;

import Model.Utilities.*;
import  Model.Statements.*;
import File.*;
import Model.Heap.*;

import java.util.Map;
import java.util.stream.Collectors;

public class ProgramState {
    private IStack<IStmt> stack;
    private IDictionary<String, Integer> SymTable;
    private IList<Integer> list;
    private Integer ID;
    private IFileTable<Integer, FileData> fileTable;
    private IHeap<Integer, Integer> heap;


    public ProgramState(IStack<IStmt> stack, IDictionary<String, Integer> symTable, IList<Integer> list,
                        IFileTable<Integer, FileData> fileTable,IHeap<Integer,Integer> heap,Integer ID) {
        this.stack = stack;
        SymTable = symTable;
        this.list = list;
        this.fileTable=fileTable;
        this.ID=ID;
        this.heap=heap;
    }

    public IStack<IStmt> getStack() {
        return stack;
    }

    public void setStack(IStack<IStmt> stack) {
        this.stack = stack;
    }
    public IFileTable<Integer, FileData> getFileTable() {
        return fileTable;
    }


    public IDictionary<String, Integer> getSymTable() {
        return SymTable;
    }

    public void setSymTable(IDictionary<String, Integer> symTable) {
        SymTable = symTable;
    }

    public IList<Integer> getList() {
        return list;
    }

    public void setList(IList<Integer> list) {
        this.list = list;
    }
    public Integer getID() {
        return ID;
    }


    public IHeap<Integer, Integer> getHeap() {
        return heap;
    }
    public void setHeapMap(Map<Integer, Integer> table) {
        heap.setMap(table);
    }



    //public void clearFileFromSym(){this.setSymTable(SymTable.getValues().stream().filter(
            //e-> fileTable.getAllValues().contains(e)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));}

    public void clearFileTable(){this.fileTable.clear();}
    public void clearFileFromSym(){this.SymTable.setMap(SymTable.getMap().entrySet().stream().filter(
            e-> fileTable.getMap().containsKey(e)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));}

    public boolean isNotCompleted(){
        return !stack.isEmpty();
    }
    public ProgramState oneStep(){

        if(stack.isEmpty())
            return null;
        //throw new EmptyStack("Stack is Empty\n");

        IStmt st = stack.pop();

        return st.execute(this);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nExeStack: \n" + this.ID + "\n");
        for(IStmt s : stack.getElements()) {
            sb.append(s).append("\n");
        }

        sb.append("\nSymTable\n");
        for(String key : SymTable.getElements()){
            sb.append(key).append(" : ").append(SymTable.getValue(key)).append("\n");
        }

        sb.append("\nHeap:\n");
        for(Integer key: heap.getAll())
           sb.append("" +key +"->"+ heap.get(key) + "\n");

        sb.append("\nOut:\n");
        for(Integer i : list.getElements()){
            sb.append(i).append("\n");
        }

        return sb.toString();

    }
}
