package pl.edu.agh.integr10s.clibuilder.shell;


import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import asg.cliche.ShellManageable;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.utils.SubShellVisitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class SubShell<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> implements ShellDependent, ShellManageable {

    private final static Logger logger = LoggerFactory.getLogger(SubShell.class);

    private AppStateT applicationState;
    private final ShellNameAware<E> subShellName;
    private final ShellNameAware<E> parentSubShellName;
    private final Class<E> clazz;
    private Shell parentShell;
    private Optional<Shell> shell = Optional.empty();
    protected Map<ShellNameAware<E>, SubShell<E, AppStateT>> childSubShells = new HashMap<>();

    public SubShell(Class<E> clazz, ShellNameAware<E> subShellName, ShellNameAware<E> parentSubShellName) {
        this.subShellName = subShellName;
        this.parentSubShellName = parentSubShellName;
        this.clazz = clazz;
    }

    public final ShellNameAware<E> getSubShellName() {
        return this.subShellName;
    }

    public final ShellNameAware<E> getParentSubShellName() {
        return parentSubShellName;
    }

    public void setApplicationState(AppStateT state) {
        logger.debug("setting application state ' {} ' in shell ' {} '", state, this);

        this.applicationState = state;

        for (SubShell<E, AppStateT> childShell : childShells()) {
            childShell.setApplicationState(state);
        }
    }

    protected final AppStateT getApplicationState() {
        return applicationState;
    }

    public final void runLevel() throws IOException {
        if (!shell.isPresent()) {
            shell = Optional.of(createShell());
            setupChildrenParentShells(shell.get());
        }

        shell.get().commandLoop();
    }

    public final void reconfigureAs(SubShell<E, AppStateT> other) {
        logger.debug("reconfiguring ' {} ' as ' {} '", this, other);

        this.parentShell = other.parentShell;
        this.shell = other.shell;
        this.childSubShells = other.childSubShells;
        this.applicationState = other.applicationState;

        postReconfigureAs(other);
    }

    protected void postReconfigureAs(SubShell<E, AppStateT> other) {
        // empty
    }

    public final Set<ShellNameAware<E>> childShellsNames() {
        return Sets.newHashSet(childSubShells.keySet());
    }

    public final Set<SubShell<E,AppStateT>> childShells() {
        return Sets.newHashSet(childSubShells.values());
    }

    private final void runChildShell(ShellNameAware<E> childName) throws IOException {
        SubShell<E, AppStateT> childShell = childSubShells.get(childName);
        logger.debug("running child shell from ' {} ' for it's child  ' {} ' ", this, childName);
        if (childShell == null) {
            logger.error("not found child sub shell ' {} ' ", childName);
        } else {
            childShell.runLevel();
        }
    }

    protected void runChildShellByName(String childName) throws IOException {
        logger.debug("running child shell by name ' {} '", childName);

        ShellNameAware<E> subShellChild = Enum.valueOf(clazz, childName.toUpperCase());
        if (subShellChild == null) {
            logger.error("there is no such subShellName '{}'", childName);
        } else {
            runChildShell(subShellChild);
        }
    }

    protected final Shell createShell() {
        if (subShellName.isMain()) {
            logger.debug("create main shell ' {} '", subShellName);
            return ShellFactory.createConsoleShell(getSubShellName().getPrompt(), getSubShellName().getDescription(), this);
        } else {
            logger.debug("create shell  ' {} '", subShellName);
            return ShellFactory.createSubshell(subShellName.getPrompt(), parentShell, subShellName.getDescription(), this);
        }
    }

    protected final void setupChildrenParentShells(Shell shell) {
        for (SubShell subShell : childSubShells.values()) {
            subShell.cliSetShell(shell);
        }
    }

    private final void addChildSubShellFlagged(SubShell<E, AppStateT> childSubShell, boolean warnReplacing) {
        logger.debug("add child sub shell {} to sub shell {}", childSubShell, this);
        SubShell<E, AppStateT> prevSubShell = childSubShells.put(childSubShell.getSubShellName(), childSubShell);
        if (prevSubShell != null) {
            if (warnReplacing) {
                logger.warn("sub shell  ' {} ' was replaced by  ' {} '", prevSubShell, childSubShell);
            } else {
                logger.debug("sub shell  ' {} ' was replaced by  ' {} '", prevSubShell, childSubShell);
            }
        }
    }

    public final void addChildSubShell(SubShell<E, AppStateT> childSubShell) {
        addChildSubShellFlagged(childSubShell, true);
    }

    public final void addChildSubShellWithReplacement(SubShell<E, AppStateT> childSubShell) {
        addChildSubShellFlagged(childSubShell, false);
    }

    public final void accept(SubShellVisitor<E, AppStateT> visitor) {
        visitor.visitSubShell(this);
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(11, 53)
                .append(subShellName)
                .append(parentSubShellName)
                .hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SubShell) {
            SubShell<E, AppStateT> other = (SubShell<E, AppStateT>) obj;
            return subShellName == other.subShellName && parentSubShellName == other.parentSubShellName;
        }
        return false;
    }

    @Override
    public String toString() {
        return "shellCategory::" + subShellName.getPrompt() + "[implementation " + getClass().getName() + "]";
    }

    @Override
    public final void cliSetShell(Shell shell) {
        this.parentShell = shell;
    }

    protected void onShellStart() {
        // empty
    }

    protected void onShellExit() {
        // empty
    }

    @Override
    public final void cliEnterLoop() {
        logger.debug("enter sub shell ' {} ' loop", subShellName);
        onShellStart();
    }

    @Override
    public final void cliLeaveLoop() {
        logger.debug("leave sub shell ' {} ' loop", subShellName);
        onShellExit();
    }
}
