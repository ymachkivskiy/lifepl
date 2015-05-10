package pl.edu.agh.integr10s.clibuilder.shell;

import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellManageable;

public abstract class SpecializedSubShell implements ShellDependent, ShellManageable {

    private final String propmt;
    private final String description;
    private Shell parentShell;

    public SpecializedSubShell(String prompt, String description) {
        this.propmt = prompt;
        this.description = description;
    }

    @Override
    public void cliSetShell(Shell shell) {
        this.parentShell = shell;
    }

    public String getPrompt() {
        return propmt;
    }

    public String getDescription() {
        return description;
    }
}
