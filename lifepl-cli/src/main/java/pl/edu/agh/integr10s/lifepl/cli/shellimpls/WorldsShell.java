package pl.edu.agh.integr10s.lifepl.cli.shellimpls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

public class WorldsShell extends SubShell<SubShellName, AppState> {

    public WorldsShell() {
        super(SubShellName.class, SubShellName.WORLDS, SubShellName.MAIN);
    }

    @Command
    public void localCommand() {
        System.out.println("worlds command" + getApplicationState().getValue());
    }

    @Command
    public void local() {
        System.out.println("some command");
    }
}
