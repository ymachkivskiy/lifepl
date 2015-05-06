package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;

public class WorldsShell extends SubShell<SubShellName> {

    public WorldsShell() {
        super(SubShellName.class, SubShellName.WORLDS, SubShellName.MAIN);
    }

    @Command
    public void localCommand() {
        System.out.println("worlds command");
    }

    @Command
    public void local() {
        System.out.println("some command");
    }
}
