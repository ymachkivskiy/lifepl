package pl.edu.agh.integr10s.lifepl.cli.shell;

import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;

public class MainShell extends CategorizedShell<ShellName,ApplicationContext> {

    public MainShell() {
        super(ShellName.class, ShellName.MAIN, ShellName.NONE);
    }

}
