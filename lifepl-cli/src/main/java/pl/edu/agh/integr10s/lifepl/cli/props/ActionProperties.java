package pl.edu.agh.integr10s.lifepl.cli.props;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.Action;

public final class ActionProperties {

    private ActionProperties() {
    }

    public static final Property<Action> ACTION_NAME = new ActionNameProperty(1);

    public static final PropertyExtractor<Action> PROPERTY_EXTRACTOR = PropertyExtractor.NewBuilder(Action.class)
            .insert(ACTION_NAME)
            .build();

    private static class ActionNameProperty extends Property<Action> {

        public ActionNameProperty(int sortKey) {
            super(StringConstants.ACTION_NAME_COLUMN, sortKey);
        }

        @Override
        public Object extract(Action propertyHost) {
            return propertyHost.getName();
        }
    }
}
