package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;

import java.util.Arrays;
import java.util.List;

public final class ConfiguredShells {

    private ConfiguredShells() {
    }

    public static List<SubShell> get() {
        return Arrays.asList(
                new MainShell(),
                new WorldsShell()
        );
    }
}
