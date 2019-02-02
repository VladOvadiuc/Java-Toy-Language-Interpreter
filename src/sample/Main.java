package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Model.Expressions.BooleanExpr;
import Model.Expressions.ConstExpr;
import Model.Expressions.VarExpr;
import Model.Utilities.*;
import Model.Statements.*;
import Model.Expressions.ArithmExpr;
import Model.ProgramState;
import Repository.*;
import File.*;
import Model.Heap.*;

import java.util.List;

import static java.lang.System.exit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("selectProgram.fxml"));

        primaryStage.setTitle("Programs");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);



        exit(0);
    }
}