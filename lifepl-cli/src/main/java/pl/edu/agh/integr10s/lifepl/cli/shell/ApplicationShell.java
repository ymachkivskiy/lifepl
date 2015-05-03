package pl.edu.agh.integr10s.lifepl.cli.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.app.MainShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.app.WorldsShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.build.AppShellBuilder;

import java.io.IOException;

public final class ApplicationShell {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationShell.class);

    private static final String APP_NAME = "Lifepl - application for integration of plans on fixed world model";

    private final SubShell rootLevel;

    public ApplicationShell(SubShell rootLevel) {
        this.rootLevel = rootLevel;
    }


    public void start() {
        try {
            this.rootLevel.runLevel();
        } catch (IOException e) {
            logger.error("error during bootstrapping application shell", e);
            System.exit(1);
        }
    }

    private static ApplicationShell createShell() {
        return new AppShellBuilder(APP_NAME)
                .addSubShell(new MainShell())
                .addSubShell(new WorldsShell())
                .build();
    }


    public static void startApp() throws IOException {
        createShell().start();
    }
}
