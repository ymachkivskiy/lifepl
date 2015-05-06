package pl.edu.agh.integr10s.lifepl.cli.shell.utils;

import pl.edu.agh.integr10s.lifepl.cli.shell.ShellNameAware;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;

public interface SubShellVisitor<E extends Enum<E> & ShellNameAware<E>> {
    void visitSubShell(SubShell<E> subShell);
}
