package pl.edu.agh.integr10s.clibuilder.utils;


import pl.edu.agh.integr10s.clibuilder.shell.ApplicationState;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

public interface SubShellVisitor<E extends Enum<E> & ShellNameAware<E>, AppStateT extends ApplicationState> {
    void visitSubShell(SubShell<E, AppStateT> subShell);
}
