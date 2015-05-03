package pl.edu.agh.integr10s.lifepl.cli.shell;

import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class SubShell implements ShellDependent {
    private final static Logger logger = LoggerFactory.getLogger(SubShell.class);

    private final SubShellName subShellName;
    private final SubShellName parentSubShellName;

    private String applicationName;
    private Shell parentShell;

    private Set<SubShell> childSubShells = new HashSet<>();

    public SubShell(SubShellName subShellName, SubShellName parentSubShellName) {
        this.subShellName = subShellName;
        this.parentSubShellName = parentSubShellName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public final SubShellName getSubShellName() {
        return this.subShellName;
    }

    public final SubShellName getParentSubShellName() {
        return parentSubShellName;
    }

    public void runLevel() throws IOException {
        ShellFactory.createSubshell(subShellName.getShellLevelName(), parentShell, getApplicationName(), this).commandLoop();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void addChildSubShell(SubShell childSubShell) {
        logger.debug("add child sub shell {} to sub shell {}", childSubShell, this);
        this.childSubShells.add(childSubShell);
    }

    @Override
    public final int hashCode() {
        //TODO implement
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        //TODO implement
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "shellCategory::" + subShellName.getShellLevelName() + "[implementation "+ getClass().getName() + "]";
    }

    @Override
    public final void cliSetShell(Shell shell) {
        this.parentShell = shell;
    }
}
