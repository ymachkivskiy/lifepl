package pl.edu.agh.integr10s.lifepl.cli;

import asg.cliche.ShellFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.Shell;
import pl.edu.agh.integr10s.lifepl.cli.shell.app.MainShell;

import java.io.IOException;

public class App
{
    public static void main(String[] args) throws IOException {
        Shell.startApp();
    }
}
