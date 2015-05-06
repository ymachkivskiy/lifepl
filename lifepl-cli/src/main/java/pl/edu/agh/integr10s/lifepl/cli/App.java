package pl.edu.agh.integr10s.lifepl.cli;

import pl.edu.agh.integr10s.clibuilder.ApplicationShell;
import pl.edu.agh.integr10s.lifepl.cli.shellimpls.ConfiguredShells;

import java.io.IOException;

public class App
{
    public static void main(String[] args) throws IOException {
        ApplicationShell.startApp(new ConfiguredShells());
    }
}
