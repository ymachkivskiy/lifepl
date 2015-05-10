package pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

public class ActorWorldProperty extends Property<Actor>{

    @Override
    public Object extract(Actor propertyHost) {
        return propertyHost.getWorld().getShortDescription();
    }

    public ActorWorldProperty(int sortKey) {
        super(StringConstants.ACTOR_WORLD_COLUMN, sortKey);
    }
}
