package pl.edu.agh.integr10s.clibuilder.utils;


import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;

public interface SubShellVisitor<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> {
    void visitSubShell(CategorizedShell<E, AppStateT> categorizedShell);
}
