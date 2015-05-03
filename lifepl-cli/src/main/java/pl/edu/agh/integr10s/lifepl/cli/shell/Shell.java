package pl.edu.agh.integr10s.lifepl.cli.shell;

import asg.cliche.ShellFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.app.MainShell;

import java.io.IOException;

public final class Shell {

    private static final String APP_NAME = "Lifepl - application for integration of plans on fixed world model";

    public static void startApp() throws IOException {
        ShellFactory.createConsoleShell("lifepl", APP_NAME, new MainShell()).commandLoop();
    }
}
