package pl.edu.agh.integr10s.lifepl.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.integr10s.clibuilder.ApplicationShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.CliConfiguration;

import java.io.IOException;

public class App
{

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/appconfig.xml");
        ApplicationShell.startApp(ctx.getBean(CliConfiguration.class));
    }
}
