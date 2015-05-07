package pl.edu.agh.integr10s.lifepl.cli.shellimpls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

public class MainShell extends SubShell<SubShellName,AppState> {

    public MainShell() {
        super(SubShellName.class, SubShellName.MAIN, SubShellName.NONE);
    }

    @Command
    public void wor() {
        System.out.println("words" + getApplicationState().getValue()); getApplicationState().setValue(122);
    }


}
