package pl.edu.agh.integr10s.lifepl.cli.shell.app;

import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShellName;

import java.io.IOException;

public class MainShell extends SubShell {

    public MainShell() {
        super(SubShellName.MAIN, SubShellName.NONE);
    }

    @Command
    public void enterPlaning() {

    }

    @Command
    public void enterWordModelsManaging() {

    }

    @Command
    public void enterWorlds() {
        try {
            childSubShells.get(SubShellName.WORLDS).runLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Shell createShell() {
        return ShellFactory.createConsoleShell(getSubShellName().getShellLevelName(), getApplicationName(), this);
    }
}
