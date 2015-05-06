package pl.edu.agh.integr10s.lifepl.cli.shell;

import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.utils.SubShellVisitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class SubShell<E extends Enum<E> & ShellNameAware<E>> implements ShellDependent {

    private final static Logger logger = LoggerFactory.getLogger(SubShell.class);

    private final ShellNameAware<E> subShellName;
    private final ShellNameAware<E> parentSubShellName;
    private final Class<E> clazz;
    private Shell parentShell;
    private Optional<Shell> shell = Optional.empty();
    protected Map<ShellNameAware<E>, SubShell> childSubShells = new HashMap<>();

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

    public final void runLevel() throws IOException {
        if (!shell.isPresent()) {
            shell = Optional.of(createShell());
            setupChildrenParentShells(shell.get());
        }

        shell.get().commandLoop();
    }

    public void reconfigureAs(SubShell<E> other) {
        logger.debug("reconfiguring ' {} ' as ' {} '", this, other);

        this.parentShell = other.parentShell;
        this.shell = other.shell;
        this.childSubShells = other.childSubShells;
    }

    public Set<ShellNameAware<E>> childShellsNames() {
        return Sets.newHashSet(childSubShells.keySet());
    }

    public Set<SubShell> childShells() {
        return Sets.newHashSet(childSubShells.values());
    }

    protected void runChildShell(ShellNameAware<E> childName) throws IOException {
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

        ShellNameAware<E> subShellChild = Enum.valueOf(clazz, childName.toUpperCase());
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

    private void addChildSubShellFlagged(SubShell<E> childSubShell, boolean warnReplacing) {
        logger.debug("add child sub shell {} to sub shell {}", childSubShell, this);
        SubShell prevSubShell = childSubShells.put(childSubShell.getSubShellName(), childSubShell);
        if (prevSubShell != null) {
            if(warnReplacing){
                logger.warn("sub shell  ' {} ' was replaced by  ' {} '", prevSubShell, childSubShell);
            }else{
                logger.debug("sub shell  ' {} ' was replaced by  ' {} '", prevSubShell, childSubShell);
            }
        }
    }

    public void addChildSubShell(SubShell childSubShell) {
        addChildSubShellFlagged(childSubShell, true);
    }

    public void addChildSubShellWithReplacement(SubShell childSubShell) {
        addChildSubShellFlagged(childSubShell, false);
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
