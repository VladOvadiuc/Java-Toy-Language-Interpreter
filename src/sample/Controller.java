package sample;

import Exception.ControllerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.ProgramState;
import File.FileData;
import File.IFileTable;
import Model.Statements.IStmt;
import Repository.IRepo;
import Model.Utilities.IStack;
import Exception.EmptyStack;
import Model.Heap.IHeap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {

    private IRepo repo;
    private ExecutorService executor;

    public Controller(IRepo _repo){
        repo = _repo;
        executor = Executors.newFixedThreadPool(5);
    }


    public int noPrgStates(){
        return repo.getPrgList().size();
    }

    public ProgramState getPrgStateByIndex(int index){
        return  repo.getPrgList().get(index);
    }

    public ObservableList<String> getPrgStatesID(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(ProgramState i : repo.getPrgList())
            list.add( String.valueOf(i.getID()));

        return list;
    }


    public List<ProgramState> removeCompletedPrg(List<ProgramState> l){
        return l.stream().filter(e-> e.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<ProgramState> list)  {


        List<Callable<ProgramState>> callList = list.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());


        List<ProgramState> newList = null;
        try {
            newList = executor.invokeAll(callList)
                    .stream()
                    .map(  future ->
                    {
                        try {
                            return future.get();
                        } catch (InterruptedException e) {
                            throw new ControllerException(e.getMessage());
                        } catch (ExecutionException e) {
                            throw new ControllerException(e.getMessage());
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new ControllerException(e.getMessage());
        }




        list.addAll(newList);
        list.forEach(prg-> repo.logPrgStateExec(prg));

        repo.setPrgList(list);

    }



    public boolean oneStepGUI (){

        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());

        if(prgList.size() > 0){
            oneStepForAll(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
            return true;
        }
        else{
            executor.shutdownNow();
            repo.setPrgList(prgList);
            return false;
        }



    }







    /*
   private Map<Integer,Integer> conservativeGarbageCollector(List<Integer> symTableValues, IHeap<Integer, Integer> heap){
        return heap.entrySet().stream()
                .filter(e->symTableValues.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    */

}