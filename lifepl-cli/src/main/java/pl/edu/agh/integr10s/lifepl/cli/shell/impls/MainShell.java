package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShellName;

import java.io.IOException;

public class MainShell extends SubShell {

    public MainShell() {
        super(SubShellName.MAIN, SubShellName.NONE);
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
