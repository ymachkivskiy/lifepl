package pl.edu.agh.integr10s.lifepl.cli.shellimpls;


import pl.edu.agh.integr10s.clibuilder.shell.CliAppConfiguration;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

import java.util.ArrayList;
import java.util.List;

public final class ConfiguredShells implements CliAppConfiguration<SubShellName, AppState> {

    private final ArrayList<SubShell<SubShellName,AppState>> shells = new ArrayList<>();

    {
        shells.add(new MainShell());
        shells.add(new WorldsShell());
    };

    @Override
    public List<SubShell<SubShellName, AppState>> getConfiguredShells() {
        return shells;
    }

    @Override
    public Class<SubShellName> getClazz() {
        return SubShellName.class;
    }

    @Override
    public AppState getInitialState() {
        return new AppState();
    }
}
