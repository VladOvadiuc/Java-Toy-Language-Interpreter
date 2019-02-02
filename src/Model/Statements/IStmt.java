package Model.Statements;

import Model.ProgramState;

public interface IStmt {
    ProgramState execute(ProgramState state);
}
