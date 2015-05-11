package pl.edu.agh.integr10s.lifepl.cli.shell;


import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.clibuilder.shell.CliAppConfiguration;

import java.util.List;

public final class CliConfiguration implements CliAppConfiguration<ShellName, ApplicationContext> {

    private List<CategorizedShell<ShellName, ApplicationContext>> shells;
    private ApplicationContext initialState;

    public void setInitialState(ApplicationContext initialState) {
        this.initialState = initialState;
    }

    public void setShells(List<CategorizedShell<ShellName, ApplicationContext>> shells) {
        this.shells = shells;
    }

    @Override
    public List<CategorizedShell<ShellName, ApplicationContext>> getConfiguredShells() {
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
