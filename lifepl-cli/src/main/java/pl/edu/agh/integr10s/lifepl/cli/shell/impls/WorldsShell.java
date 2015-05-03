package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShellName;

public class WorldsShell extends SubShell{

    public WorldsShell() {
        super(SubShellName.WORLDS, SubShellName.MAIN);
    }

    @Command
    public void worldsCommand() {
        System.out.println("worlds command");
    }
}
