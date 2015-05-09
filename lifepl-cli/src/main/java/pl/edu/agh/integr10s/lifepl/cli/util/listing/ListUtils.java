package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

public final class ListUtils {

    public static PropertyExtractor<Actor> ActorPropertyExtractor() {
        return PropertyExtractor.NewBuilder(Actor.class)

                .insert(
                        new Property<Actor>("name") {
                            @Override
                            public Object extract(Actor propertyHost) {
                                return propertyHost.getName();
                            }
                        }
                )

                .build();

    }
}
