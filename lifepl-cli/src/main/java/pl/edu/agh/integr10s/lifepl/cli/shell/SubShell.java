package pl.edu.agh.integr10s.lifepl.cli.shell;

import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.SubShellVisitor;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class SubShell implements ShellDependent {

    private final static Logger logger = LoggerFactory.getLogger(SubShell.class);

    private final SubShellName subShellName;
    private final SubShellName parentSubShellName;
    protected Map<SubShellName, SubShell> childSubShells = new HashMap<>();
    private Shell parentShell;
    private Optional<Shell> shell = Optional.empty();

    public SubShell(SubShellName subShellName, SubShellName parentSubShellName) {
        this.subShellName = subShellName;
        this.parentSubShellName = parentSubShellName;
    }

    public final SubShellName getSubShellName() {
        return this.subShellName;
    }

    public final SubShellName getParentSubShellName() {
        return parentSubShellName;
    }

    public final void runLevel() throws IOException {
        if (!shell.isPresent()) {
            shell = Optional.of(createShell());
            setupChildrenParentShells(shell.get());
        }

        shell.get().commandLoop();
    }

    public void reconfigureAs(SubShell other) {
        this.parentShell = other.parentShell;
        this.shell = other.shell;
        this.childSubShells = other.childSubShells;
    }

    public EnumSet<SubShellName> childShells() {
        return EnumSet.copyOf(childSubShells.keySet());
    }

    protected void runChildShell(SubShellName childName) throws IOException {
        SubShell childShell = childSubShells.get(childName);
        logger.debug("running child shell from ' {} ' for it's child  ' {} ' ", this, childName);
        if (childShell == null) {
            logger.error("not found child sub shell ' {} ' ", childName);
        } else {
            childShell.runLevel();
        }
    }

    protected void runChildShellByName(String childName) throws IOException {
        logger.debug("running child shell by name ' {} '", childName);
        SubShellName subShellChild = SubShellName.valueOf(childName.toUpperCase());
        if (subShellChild == null) {
            logger.error("there is no such subShellName '{}'", childName);
        } else {
            runChildShell(subShellChild);
        }
    }

    protected Shell createShell() {
        return ShellFactory.createSubshell(subShellName.getPrompt(), parentShell, subShellName.getDescription(), this);
    }

    protected void setupChildrenParentShells(Shell shell) {
        for (SubShell subShell : childSubShells.values()) {
            subShell.cliSetShell(shell);
        }
    }

    public void addChildSubShell(SubShell childSubShell) {
        logger.debug("add child sub shell {} to sub shell {}", childSubShell, this);
        SubShell prevSubShell = childSubShells.put(childSubShell.getSubShellName(), childSubShell);
        if (prevSubShell != null) {
            logger.warn("sub shell  ' {} ' was replaced", prevSubShell);
        }
    }

    public void accept(SubShellVisitor visitor) {

        visitor.visitSubShell(this);
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
        return "shellCategory::" + subShellName.getPrompt() + "[implementation " + getClass().getName() + "]";
    }

    @Override
    public final void cliSetShell(Shell shell) {
        this.parentShell = shell;
    }
}
