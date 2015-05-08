package pl.edu.agh.integr10s.lifepl.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.integr10s.clibuilder.ApplicationShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.CliConfiguration;

import java.io.IOException;

public class App
{

    private static final String SPRING_CONFIGS[] =
            {
                    "spring/injection_config.xml",
                    "spring/persistence.xml",
                    "spring/shells.xml"
            };

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_CONFIGS);

        CliConfiguration cliConfiguration = ctx.getBean(CliConfiguration.class);

        ApplicationShell.startApp(cliConfiguration);
    }
}
