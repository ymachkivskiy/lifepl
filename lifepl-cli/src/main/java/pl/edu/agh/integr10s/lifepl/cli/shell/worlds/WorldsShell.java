package pl.edu.agh.integr10s.lifepl.cli.shell.worlds;

import asg.cliche.Command;
import asg.cliche.Param;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.lifepl.cli.props.WorldProperties;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistance.common.WorldsRepository;

import java.util.Collection;
import java.util.Optional;

public class WorldsShell extends CategorizedShell<ShellName, ApplicationContext> {

    public WorldsShell() {
        super(ShellName.class, ShellName.WORLDS, ShellName.MAIN);
    }

    public static Listing<World> listing(Collection<World> worlds) {
        return Listing.For(worlds, WorldProperties.PROPERTY_EXTRACTOR);
    }

    @Command(name = "list", abbrev = "ls", description = "List all available world models")
    public void listWorlds() {
        listing(repository().getWorlds()).list();
    }

    public WorldsRepository repository() {
        return getApplicationState().getWorldsRepository();
    }

    @Command(name = "delete", abbrev = "rm", description = "Delete world model")
    public void deleteWorld() {
        Optional<World> worldToDelete = listing(repository().getWorlds()).choose();
        if (worldToDelete.isPresent()) {
            repository().removeWorld(worldToDelete.get());
        }
    }

    @Command(name = "create", abbrev = "new", description = "Create new world model")
    public void createWorld(@Param(name = "world name", description = "name of new world model") String name) {
        World world = new World(name, null, null); //TODO change it!!!!!!!!!!!!!!!!
        repository().addWorld(world);
    }

    @Command(name = "view", abbrev = "v", description = "View existing world model")
    public void editWorld() {
        Optional<World> worldToEdit = listing(repository().getWorlds()).choose();
        if (worldToEdit.isPresent()) {
            editWorld(worldToEdit.get());
        }
    }

    private void editWorld(World world) {
        runSpecializedShell(new WorldEditShell(repository(), world));
    }
}
