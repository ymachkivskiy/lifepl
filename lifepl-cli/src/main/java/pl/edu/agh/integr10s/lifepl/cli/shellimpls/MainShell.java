package pl.edu.agh.integr10s.lifepl.cli.shellimpls;

import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

import java.io.IOException;

public class MainShell extends SubShell<SubShellName> {

    public MainShell() {
        super(SubShellName.class, SubShellName.MAIN, SubShellName.NONE);
    }

    public void ew() {
        try {
            runChildShell(SubShellName.WORLDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Shell createShell() {
        return ShellFactory.createConsoleShell(getSubShellName().getPrompt(), getSubShellName().getDescription(), this);
    }
}
