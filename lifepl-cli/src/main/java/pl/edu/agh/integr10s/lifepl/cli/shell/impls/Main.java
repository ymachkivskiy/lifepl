package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import pl.edu.agh.integr10s.clibuilder.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;

public class Main extends SubShell<ShellName,ApplicationContext> {

    public Main() {
        super(ShellName.class, ShellName.MAIN, ShellName.NONE);
    }

}
