package pl.edu.agh.integr10s.lifepl.cli.shell.impls.planing;


import pl.edu.agh.integr10s.engine.resolve.EngineFactory;
import pl.edu.agh.integr10s.engine.resolve.Planning;
import pl.edu.agh.integr10s.engine.resolve.PlanningEngine;
import pl.edu.agh.integr10s.engine.resolve.ProblemContext;
import pl.edu.agh.integr10s.lifepl.cli.props.ActorProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.EngineProperties;
import pl.edu.agh.integr10s.lifepl.cli.props.WorldProperties;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Property;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.PropertyExtractor;
import pl.edu.agh.integr10s.lifepl.model.actor.Actor;
import pl.edu.agh.integr10s.lifepl.model.world.World;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class PlaningConfiguration {

    private static final String SETTING_NAME_COLUMN = "Setting Name";
    private static final String SETTING_VALUE_COLUMN = "Value";

    private static final Property<PlaningSetting> NAME_PROPERTY = new NameProperty(1);
    private static final Property<PlaningSetting> VALUE_PROPERTY = new ValueProperty(2);
    private static final PropertyExtractor<PlaningSetting> PROPERTY_EXTRACTOR = PropertyExtractor.NewBuilder(PlaningSetting.class)
        .insert(NAME_PROPERTY)
        .insert(VALUE_PROPERTY)
        .build();


    private final Listing<EngineFactory> enginesListing;
    private final Listing<World> worldsListing;
    private final ProblemContext problemContext;
    private World world;
    private EngineFactory engineFactory;

    public PlaningConfiguration(ApplicationContext applicationContext) {
        List<EngineFactory> engineFactories = applicationContext.getPlaningEnginesFactories();
        enginesListing = Listing.For(engineFactories, EngineProperties.PROPERTY_EXTRACTOR);
        engineFactory = engineFactories.get(0);

        Collection<World> worlds = applicationContext.getWorldsService().getWorlds();
        worldsListing = Listing.For(worlds, WorldProperties.PROPERTY_EXTRACTOR);
        world = worlds.iterator().next();


        problemContext = new ProblemContext();

        problemContext.setWorld(world);
    }

    Planning performPlanning() {
        PlanningEngine engine = engineFactory.newEngine();
        return engine.performPlaning(problemContext);
    }

    public void setWorld(World worldModel) {
        world = worldModel;
    }

    public void addActors(Collection<Actor> chosenActors) {
        problemContext.addActors(chosenActors);
    }

    public Listing<EngineFactory> engineFactoryListing() {
        return enginesListing;
    }

    public Listing<World> worldListing() {
        return worldsListing;
    }

    public Listing<Actor> actorListing() {
        return Listing.For(world.getActors(), ActorProperties.PROPERTY_EXTRACTOR_WORLD);
    }

    public void setEngineFactory(EngineFactory engineFactory) {
        this.engineFactory = engineFactory;
    }


    public Listing<PlaningSetting> createListing() {
        return Listing.For(
            Arrays.asList(
                new EngineSetting(engineFactory),
                new WorldSetting(world),
                new ActorsSetting(problemContext.getProblemActors())
            ),
            PROPERTY_EXTRACTOR
        );
    }

    public static abstract class PlaningSetting {
        private final String name;
        private final String value;

        public PlaningSetting(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    private static class EngineSetting extends PlaningSetting {
        public EngineSetting(EngineFactory engineFactory) {
            super(
                "Selected engine",
                String.format("%s (%s) [%s]", engineFactory.getEngineDescription().getName(), engineFactory.getEngineDescription().getDescription(), engineFactory.getEngineDescription().getAlgorithmComplexity())
            );
        }
    }

    private static class WorldSetting extends PlaningSetting {
        public WorldSetting(World world) {
            super(
                "Selected world model",
                String.format("%s (%s) [Actions# %d]", world.getName(), world.getDescription(), world.getAllowedActions().size())
            );
        }
    }

    private static class ActorsSetting extends PlaningSetting {
        public ActorsSetting(Collection<Actor> actors) {
            super(
                "Selected actors",
                toActorsFormattedList(actors)
            );
        }

        public static String toActorsFormattedList(Collection<Actor> actors) {
            StringBuilder sb = new StringBuilder();

            for (Actor actor : actors) {
                sb.append(String.format("* %s [Goals # %d]\n", actor.getName(), actor.getGoals().size()));
            }

            return sb.toString();
        }
    }

    private static class NameProperty extends Property<PlaningSetting> {

        public NameProperty(int sortKey) {
            super(SETTING_NAME_COLUMN, sortKey);
        }

        @Override
        public Object extract(PlaningSetting propertyHost) {
            return propertyHost.getName();
        }
    }

    private static class ValueProperty extends Property<PlaningSetting> {
        public ValueProperty(int sortKey) {
            super(SETTING_VALUE_COLUMN, sortKey);
        }

        @Override
        public Object extract(PlaningSetting propertyHost) {
            return propertyHost.getValue();
        }
    }

}
