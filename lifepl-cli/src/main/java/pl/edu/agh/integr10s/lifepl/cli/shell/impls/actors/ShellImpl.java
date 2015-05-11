package pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors;

import asg.cliche.Command;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.persistance.actors.ActorsService;

import java.util.Collection;
import java.util.Optional;

public class ShellImpl extends CategorizedShell<ShellName, ApplicationContext> {

    public ShellImpl() {
        super(ShellName.class, ShellName.ACTORS, ShellName.MAIN);
    }

    private static Listing<Actor> listing(Collection<Actor> actors) {
        return Listing.For(actors, ActorProperties.PROPERTY_EXTRACTOR_GLOBAL);
    }

    private ActorsService service() {
        return getApplicationState().getActorsService();
    }

    @Command(name = "list", abbrev = "ls", description = "List stored actors")
    public void listAllActors() {
        listing(service().getActors()).list();
    }

    @Command(name = "delete", abbrev = "rm", description = "Delete actor")
    public void deleteActor() {
        Optional<Actor> actor = listing(service().getActors()).choose();
        if (actor.isPresent()) {
            service().removeActor(actor.get());
        }
    }
}
