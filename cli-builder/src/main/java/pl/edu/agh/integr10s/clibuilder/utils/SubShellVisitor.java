package pl.edu.agh.integr10s.clibuilder.utils;


import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

public interface SubShellVisitor<E extends Enum<E> & ShellNameAware<E>> {
    void visitSubShell(SubShell<E> subShell);
}
