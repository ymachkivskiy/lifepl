package pl.edu.agh.integr10s.lifepl.cli.shell;


import pl.edu.agh.integr10s.clibuilder.shell.CliAppConfiguration;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

import java.util.List;

public final class CliConfiguration implements CliAppConfiguration<ShellName, ApplicationContext> {

    private List<SubShell<ShellName, ApplicationContext>> shells;
    private ApplicationContext initialState;

    public void setInitialState(ApplicationContext initialState) {
        this.initialState = initialState;
    }

    public void setShells(List<SubShell<ShellName, ApplicationContext>> shells) {
        this.shells = shells;
    }

    @Override
    public List<SubShell<ShellName, ApplicationContext>> getConfiguredShells() {
        return shells;
    }

    @Override
    public Class<ShellName> getClazz() {
        return ShellName.class;
    }

    @Override
    public ApplicationContext getInitialState() {
        return initialState;
    }
}
