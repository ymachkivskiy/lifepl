package pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

public final class ActorProperties {
    private ActorProperties() {
    }

    public static final Property<Actor> ACTOR_NAME_PROPERTY = new ActorNameProperty(0);
    public static final Property<Actor> ACTOR_WORLD_PROPERTY = new ActorWorldProperty(1);

    public static final PropertyExtractor<Actor> PROPERTY_EXTRACTOR_GLOBAL = PropertyExtractor.NewBuilder(Actor.class)
            .insert(ActorProperties.ACTOR_NAME_PROPERTY)
            .insert(ActorProperties.ACTOR_WORLD_PROPERTY)
            .build();

    public static final PropertyExtractor<Actor> PROPERTY_EXTRACTOR_WORLD = PropertyExtractor.NewBuilder(Actor.class)
            .insert(ActorProperties.ACTOR_NAME_PROPERTY)
            .build();


    private static class ActorNameProperty extends Property<Actor> {

        @Override
        public Object extract(Actor propertyHost) {
            return propertyHost.getName();
        }

        public ActorNameProperty(int sortIdx) {
            super(StringConstants.ACTOR_NAME_COLUMN, sortIdx);
        }
    }

    private static class ActorWorldProperty extends Property<Actor>{

        @Override
        public Object extract(Actor propertyHost) {
            return propertyHost.getWorld().getDescription();
        }

        public ActorWorldProperty(int sortKey) {
            super(StringConstants.ACTOR_WORLD_COLUMN, sortKey);
        }
    }
}
