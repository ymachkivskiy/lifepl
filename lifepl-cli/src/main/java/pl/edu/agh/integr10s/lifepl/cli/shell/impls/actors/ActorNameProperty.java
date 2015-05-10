package pl.edu.agh.integr10s.lifepl.cli.shell.impls.actors;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;

public class ActorNameProperty extends Property<Actor> {

    @Override
    public Object extract(Actor propertyHost) {
        return propertyHost.getName();
    }

    public ActorNameProperty(int sortIdx) {
        super(StringConstants.ACTOR_NAME_COLUMN, sortIdx);
    }
}
