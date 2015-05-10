package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors.ActorNameProperty;
import pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors.ActorWorldProperty;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

public final class ListUtils {

    public static PropertyExtractor<Actor> ActorPropertyExtractor() {
        return PropertyExtractor.NewBuilder(Actor.class)
                .insert(new ActorNameProperty(1))
                .insert(new ActorWorldProperty(2))
                .build();

    }
}
