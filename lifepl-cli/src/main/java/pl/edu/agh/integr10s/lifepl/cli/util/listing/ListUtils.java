package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors.ActorNameProperty;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors.ActorWorldProperty;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds.WorldActorsNumberProperty;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds.WorldAllowedActionsNumberProperty;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds.WorldDescriptionProperty;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds.WorldNameProperty;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public final class ListUtils {

    public static PropertyExtractor<Actor> ActorPropertyExtractor() {
        return PropertyExtractor.NewBuilder(Actor.class)
                .insert(new ActorNameProperty(1))
                .insert(new ActorWorldProperty(2))
                .build();

    }

    public static PropertyExtractor<World> WorldsPropertyExtractor() {
        return PropertyExtractor.NewBuilder(World.class)
                .insert(new WorldNameProperty())
                .insert(new WorldDescriptionProperty())
                .insert(new WorldActorsNumberProperty())
                .insert(new WorldAllowedActionsNumberProperty())
                .build();

    }
}
