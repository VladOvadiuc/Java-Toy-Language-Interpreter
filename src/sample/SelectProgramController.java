package sample;

import File.CloseFile;
import File.OpenFile;
import File.ReadFile;
import Model.Expressions.BooleanExpr;
import Model.Heap.HeapAlloc;
import Model.Heap.HeapReading;
import Model.Heap.HeapWriting;
import Model.Utilities.IStack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Expressions.ArithmExpr;
import Model.Expressions.ConstExpr;
import Model.Expressions.VarExpr;
import Model.Statements.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SelectProgramController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button runProgramBTN;


    private List<IStmt> StmtList = new ArrayList<IStmt>();


    @FXML
    public void initialize() {

        IStmt ex1=new CompStmt(new AssignStmt("a", new ArithmExpr("-", new ConstExpr(2), new ConstExpr(2))),
                new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ConstExpr(2)), new AssignStmt("v",
                        new ConstExpr(3))), new PrintStmt(new VarExpr("v"))));

        StmtList.add(ex1);
        IStmt ex2= new CompStmt(new OpenFile("fis","test.txt"),
                new CompStmt(new ReadFile((new VarExpr("fis")),"a"),
                        new CompStmt(new PrintStmt(new VarExpr("a")),
                                new CompStmt(new IfStmt(new VarExpr("a"),
                                        new CompStmt(new CompStmt(new OpenFile("var","./text.txt"),new ReadFile(new VarExpr("var"),"b")),
                                                new PrintStmt(new VarExpr("b"))),new PrintStmt(new ConstExpr(0))),new CloseFile(new VarExpr("fis"))))));

        StmtList.add(ex2);
        IStmt ex3= new CompStmt(new AssignStmt("v",new ConstExpr(10)),new CompStmt(new HeapAlloc("v",new ConstExpr(20)),
                new CompStmt(new HeapAlloc("a",new ConstExpr(22)),new CompStmt(new AssignStmt("a",new ConstExpr(17)),new PrintStmt(new HeapReading("v"))))));
        StmtList.add(ex3);
        IStmt expr4 = new CompStmt(new AssignStmt("v",new ConstExpr(10)),new CompStmt(
                new HeapAlloc("a",new ConstExpr(22)),new CompStmt(
                new forkStmt(new CompStmt(new HeapWriting("a",new ConstExpr(30)),new CompStmt(
                        new AssignStmt("v",new ConstExpr(32)),new CompStmt(
                        new PrintStmt(new VarExpr("v")),new PrintStmt(new HeapReading("a"))
                )))),new CompStmt(
                new PrintStmt(new VarExpr("v")),new PrintStmt(new HeapReading("a"))
        ))));
        StmtList.add(expr4);


            //v=0;
        //(repeat (fork(print(v);v=v-1);v=v+1) until v==3);
        //x=1;y=2;z=3;w=4;
        //print(v*10)
        IStmt ex5 = new CompStmt(new AssignStmt("v",new ConstExpr(3)),new RepeatStmt(
                new CompStmt(new PrintStmt(new VarExpr("v")),new AssignStmt("v",new ArithmExpr("-",new VarExpr("v"),
                        new ConstExpr(1)))),new BooleanExpr("<=",new VarExpr("v"),new ConstExpr(0))) );
        StmtList.add(ex5);

        IStmt ex6 =  new forStmt(new AssignStmt("i",new ConstExpr(3)),new BooleanExpr(">=",new VarExpr("i"),new ConstExpr(0)),new AssignStmt("i",new ArithmExpr("-",new VarExpr("i"),
                new ConstExpr(1))),new PrintStmt(new VarExpr("i")));

        StmtList.add(ex6);



        //a=1;b=2;c=5;
        //switch(a*10)
        // (case (b*c) print(a);print(b))
        // (case (10) print(100);print(200))
        // (default print(300));
        //print(300)
        IStmt ex7 = new CompStmt(new AssignStmt("a",new ConstExpr(1)),new CompStmt(new AssignStmt("b",new ConstExpr(2)),
                new CompStmt(new AssignStmt("c",new ConstExpr(5)),new CompStmt(new SwitchStmt(new ArithmExpr("*",new VarExpr("a"),new ConstExpr(10)),
                        new ArithmExpr("*",new VarExpr("b"),new VarExpr("c")) ,
                        new CompStmt(new PrintStmt(new VarExpr("a")),new PrintStmt(new VarExpr("b"))),
                        new ConstExpr(10),
                        new CompStmt(new PrintStmt(new ConstExpr(100)),new PrintStmt(new ConstExpr(200))),
                        new PrintStmt(new ConstExpr(300))),new PrintStmt(new ConstExpr(300)) ))));
        StmtList.add(ex7);
        ObservableList<String> list = FXCollections.observableArrayList();
        for(IStmt i : StmtList)
            list.add(""+i);

        listView.setItems(list);

        listView.getSelectionModel().selectIndices(0);
    }

    @FXML
    public void buttonRun() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("runProgram.fxml"));
        loader.setController(new RunProgramController(StmtList.get(listView.getSelectionModel().getSelectedIndex())));

        Stage stage = new Stage();
        Parent root = loader.load();
        stage.setTitle("Running Program");
        stage.setScene(new Scene(root, 800, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

}