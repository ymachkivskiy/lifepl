package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import pl.edu.agh.integr10s.lifepl.cli.shell.ConfiguredShellsProvider;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;

import java.util.ArrayList;
import java.util.List;

public final class ConfiguredShells implements ConfiguredShellsProvider<SubShellName> {

    private final ArrayList<SubShell<SubShellName>> shells = new ArrayList<>();

    {
        shells.add(new MainShell());
        shells.add(new WorldsShell());
    };

    @Override
    public List<SubShell<SubShellName>> getConfiguredShells() {
        return shells;
    }

    @Override
    public Class<SubShellName> getClazz() {
        return SubShellName.class;
    }
}
