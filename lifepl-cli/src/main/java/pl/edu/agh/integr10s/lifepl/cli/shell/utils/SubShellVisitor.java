package pl.edu.agh.integr10s.lifepl.cli.shell.utils;

import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;

public interface SubShellVisitor {
    void visitSubShell(SubShell subShell);
}
