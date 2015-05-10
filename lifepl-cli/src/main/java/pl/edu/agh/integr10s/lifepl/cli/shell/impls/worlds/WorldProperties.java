package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.StringConstants;
import pl.edu.agh.integr10s.lifepl.model.world.World;

public final class WorldProperties {

    public static final Property<World> WORLD_NAME = new WorldNameProperty(1);
    public static final Property<World> WORD_DESCRIPTION = new WorldDescriptionProperty(2);
    public static final Property<World> WORLD_ACTORS_NUMBER = new WorldActorsNumberProperty(3);
    public static final Property<World> WORLD_ALLOWED_ACTIONS_NUMBER = new WorldAllowedActionsNumberProperty(4);

    public static final PropertyExtractor<World> PROPERTY_EXTRACTOR = PropertyExtractor.NewBuilder(World.class)
            .insert(WorldProperties.WORLD_NAME)
            .insert(WorldProperties.WORD_DESCRIPTION)
            .insert(WorldProperties.WORLD_ACTORS_NUMBER)
            .insert(WorldProperties.WORLD_ALLOWED_ACTIONS_NUMBER)
            .build();


    private WorldProperties() {
    }

    private static class WorldActorsNumberProperty extends Property<World> {

        public WorldActorsNumberProperty(int sortIdx) {
            super(StringConstants.WORLD_ACTORS_NUMBER_COLUMN, sortIdx);
        }

        @Override
        public Object extract(World propertyHost) {
            return propertyHost.getActors().size();
        }
    }

    private static class WorldAllowedActionsNumberProperty extends Property<World> {

        public WorldAllowedActionsNumberProperty(int sortIdx) {
            super(StringConstants.WORLD_ALLOWED_ACTIONS_COLUMN, sortIdx);
        }

        @Override
        public Object extract(World propertyHost) {
            return propertyHost.getAllowedActions().size();
        }
    }

    private static class WorldDescriptionProperty extends Property<World> {

        public WorldDescriptionProperty(int sortIdx) {
            super(StringConstants.WORLD_DESCRIPTION_COLUMN, sortIdx);
        }

        @Override
        public Object extract(World propertyHost) {
            return propertyHost.getDescription();
        }
    }

    private static class WorldNameProperty extends Property<World> {

        public WorldNameProperty(int sortIdx) {
            super(StringConstants.WORLD_NAME_COLUMN, sortIdx);
        }

        @Override
        public Object extract(World propertyHost) {
            return propertyHost.getName();
        }
    }
}
