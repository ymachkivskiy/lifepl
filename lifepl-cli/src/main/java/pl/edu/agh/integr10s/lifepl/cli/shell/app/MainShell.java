package pl.edu.agh.integr10s.lifepl.cli.shell.app;

import asg.cliche.Command;
import pl.edu.agh.integr10s.lifepl.cli.shell.AbstractShellLevel;

public class MainShell extends AbstractShellLevel{

    private static final String PROMPT = "lifepl";


    public MainShell() {
        super(PROMPT);
    }

    @Command
    public void enterPlaning() {

    }

    @Command
    public void enterWordModelsManaging() {

    }

}
