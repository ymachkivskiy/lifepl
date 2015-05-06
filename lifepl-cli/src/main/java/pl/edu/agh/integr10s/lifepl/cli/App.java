package pl.edu.agh.integr10s.lifepl.cli;

import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.ConfiguredShells;

import java.io.IOException;

public class App
{
    public static void main(String[] args) throws IOException {
        ApplicationShell.startApp(new ConfiguredShells());
    }
}
