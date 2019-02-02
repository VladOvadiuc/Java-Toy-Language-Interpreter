package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.ProgramState;
import Model.Expressions.ArithmExpr;
import Model.Expressions.VarExpr;
import File.*;
import Model.Statements.*;
import Repository.Repository;
import Model.Utilities.*;
import utilsTables.*;
import Model.Heap.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class RunProgramController {

    //HEAP TABLE
    @FXML private TableView<HeapTableView> heapTable;
    @FXML private TableColumn<HeapTableView, Integer> heapAddressColumn;
    @FXML private TableColumn<HeapTableView, Integer> heapValueColumn;

    //FILE TABLE
    @FXML private TableView<fileTableView> fileTable;
    @FXML private TableColumn<fileTableView, Integer> fileTableIdentifier;
    @FXML private TableColumn<fileTableView, String> fileTableName;

    //SYM TABLE
    @FXML private TableView<SymTableView> symTable;
    @FXML private TableColumn<SymTableView, String> symTableVarName;
    @FXML private TableColumn<SymTableView, Integer> symTableValue;

    @FXML private ListView<String> outList;
    @FXML private ListView<String> exeStack;
    @FXML private ListView<String> prgStateIdentifiers;
    @FXML private Button oneStepBTN;
    @FXML private TextField noPrgStateTextField;

/*
    @FXML private TableView<ProcTableView> ProcTable;
    @FXML private TableColumn<ProcTableView, String> ProcTableCol1;
    @FXML private TableColumn<ProcTableView, String> ProcTableBody;*/

    private Controller ctrl;
    private IStmt statement;

    public RunProgramController(IStmt statement){
        this.statement = statement;
    }

    @FXML
    public void initialize(){

        IStack<IStmt> exeStack = new Stack<>();
        IDictionary<String, Integer> dict = new Dictionary<>();
        Deque<IDictionary> stackSymTable = new ArrayDeque<>();
        IList<Integer> list = new MyList<>();
        IFileTable<Integer, FileData> fileTable = new FileTable<>();
        IHeap<Integer, Integer> heap = new Heap<>();
        IDictionary<String,Integer> x = new Dictionary<>();

        ArrayList<String> l1 = new ArrayList<>();
        l1.add("a");l1.add("b");
        IStmt s1 = new AssignStmt("v", new ArithmExpr("+", new VarExpr("a"), new VarExpr("b")));
        IStmt s2 = new PrintStmt(new VarExpr("v"));
        IStmt ex1 = new CompStmt(s1,s2);

        ArrayList<String> l2 = new ArrayList<>();
        l2.add("a"); l2.add("b");
        IStmt s3 = new AssignStmt("v", new ArithmExpr("*", new VarExpr("a"), new VarExpr("b")));
        IStmt s4 = new PrintStmt(new VarExpr("v"));
        IStmt ex2 = new CompStmt(s3,s4);





        stackSymTable.push(x);
        exeStack.add(statement);
        //ProgramState programState = new ProgramState(exeStack, stackSymTable, list, null, fileTable, heap, GenIDFork.getID());
        ProgramState programState = new ProgramState(exeStack,dict,list,fileTable,heap,1);

        Repository repo = new Repository();
        repo.addPrg(programState);
        ctrl = new Controller(repo);


        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.fileTableIdentifier.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fileTableName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        this.symTableVarName.setCellValueFactory(new PropertyValueFactory<>("varName"));
        this.symTableValue.setCellValueFactory(new PropertyValueFactory<>("value"));


        setNoPrgState();
        setPrgStateIdentifiers();
        prgStateIdentifiers.getSelectionModel().select(0);
        setExeStack();
    }



    private void setNoPrgState(){
        Integer nr = ctrl.noPrgStates();
        noPrgStateTextField.setText(String.valueOf(nr));
    }

    private void setPrgStateIdentifiers(){
        prgStateIdentifiers.setItems(ctrl.getPrgStatesID());
    }

    ProgramState getCurrentProgramState(){
        int index = prgStateIdentifiers.getSelectionModel().getSelectedIndex();
        if(index == -1)
            index = 0;
        return ctrl.getPrgStateByIndex(index);
    }

    private void setExeStack(){

        ObservableList<String> list = FXCollections.observableArrayList();
        ProgramState programState = getCurrentProgramState();


        for(IStmt i : programState.getStack().getElements())
            list.add(""+i);

        exeStack.setItems(list);
    }

    private void setHeapTable(){

        ObservableList<HeapTableView> list = FXCollections.observableArrayList();
        ProgramState programState = getCurrentProgramState(); // here we don't need to get current because heap is shared by all
        // but i used to don't make another function to get one programState

        for(Integer key: programState.getHeap().getAll())
            list.add(new HeapTableView(key, programState.getHeap().get(key)));

        heapTable.setItems(list);
    }



    private void setFileTable(){

        ObservableList<fileTableView> list = FXCollections.observableArrayList();
        ProgramState programState = getCurrentProgramState();

        for(Integer key: programState.getFileTable().getAllKeys())
            list.add(new fileTableView(key, programState.getFileTable().get(key).getFileName()));

        fileTable.setItems(list);
    }

    private void setSymTable(){

        ObservableList<SymTableView> list = FXCollections.observableArrayList();
        ProgramState programState = getCurrentProgramState();
        IDictionary<String, Integer> dict= programState.getSymTable();

        for(String key: dict.getElements())
            list.add(new SymTableView(key, dict.getValue(key)));

        symTable.setItems(list);
    }

    private void setOutList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        ProgramState programState = getCurrentProgramState();   // they all share the same outList

        for(Integer i: programState.getList().getElements())
            list.add(""+i);

        outList.setItems(list);
    }


    private void setAll(){
        setNoPrgState();
        setPrgStateIdentifiers();
        setExeStack();
        setHeapTable();
        setFileTable();
        setSymTable();
        setOutList();
    }



    public void executeOneStep(ActionEvent ae){

        try {
            if (ctrl.oneStepGUI()) {
                setAll();
            } else {
                setNoPrgState();
                setPrgStateIdentifiers();
                symTable.getItems().clear();
            }
        }
        catch (RuntimeException e){
            Node source = (Node) ae.getSource();
            Stage theStage = (Stage) source.getScene().getWindow();
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
            theStage.close();
        }
    }

    public void mouseClickPrgStateIdentifiers(){

        if(ctrl.noPrgStates() > 0)
            setAll();
    }
}