package pl.edu.agh.integr10s.lifepl.cli.shell.impls;

import asg.cliche.Command;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

import java.util.Collection;

public class Actors extends SubShell<ShellName, ApplicationContext> {

    public Actors() {
        super(ShellName.class, ShellName.ACTORS, ShellName.MAIN);
    }

    @Command(name="list", abbrev = "ls", description = "List stored actors")
    public void listAllActors() {
        Collection<Actor> actors = getApplicationState().getActorsService().getActors();
//TODO change using table
        for (Actor actor : actors) {
            System.out.println(actor);
        }
    }
}
