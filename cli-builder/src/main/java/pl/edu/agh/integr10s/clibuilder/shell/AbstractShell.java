package pl.edu.agh.integr10s.clibuilder.shell;

import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import asg.cliche.ShellManageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public abstract class AbstractShell implements ShellDependent, ShellManageable {

    private final static Logger logger = LoggerFactory.getLogger(CategorizedShell.class);
    private final String name;
    protected Shell parentShell;
    protected Optional<Shell> shell = Optional.empty();

    public AbstractShell(String name) {
        this.name = name;
    }

    public final void runSpecializedShell(SpecializedSubShell specializedSubShell) {
        logger.debug("run specialized shell {} from within {}", specializedSubShell, this);
        try {
            ShellFactory.createSubshell(specializedSubShell.getPrompt(), shell.get(), specializedSubShell.getDescription(), specializedSubShell).commandLoop();
        } catch (IOException e) {
            logger.error("error during running specialized shell", e);
        }
    }

    protected void onShellEnter() {
        // empty
    }

    protected void onShellExit() {
        // empty
    }

    @Override
    public final void cliSetShell(Shell shell) {
        this.parentShell = shell;
    }

    @Override
    public final void cliEnterLoop() {
        logger.debug("enter sub shell ' {} ' loop", name);
        onShellEnter();
    }

    @Override
    public final void cliLeaveLoop() {
        onShellExit();
        logger.debug("leave sub shell ' {} ' loop", name);
    }
}
