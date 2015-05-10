package pl.edu.agh.integr10s.lifepl.cli.shell.impls.worlds;

import asg.cliche.Command;
import asg.cliche.Param;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.ApplicationContext;
import pl.edu.agh.integr10s.lifepl.cli.shell.ShellName;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.ListUtils;
import pl.edu.agh.integr10s.lifepl.cli.util.listing.Listing;
import pl.edu.agh.integr10s.lifepl.model.world.World;
import pl.edu.agh.integr10s.lifepl.persistance.worlds.WorldsService;

import java.util.Collection;
import java.util.Optional;

public class ShellImpl extends SubShell<ShellName, ApplicationContext> {

    public ShellImpl() {
        super(ShellName.class, ShellName.WORLDS, ShellName.MAIN);
    }

    @Command(name = "list", abbrev = "ls", description = "List all available world models")
    public void listWorlds() {
        listing(service().getWorlds()).list();
    }

    public static Listing<World> listing(Collection<World> worlds) {
        return Listing.For(worlds, ListUtils.WorldsPropertyExtractor());
    }

    public WorldsService service() {
        return getApplicationState().getWorldsService();
    }

    @Command(name = "delete", abbrev = "rm", description = "Delete world model")
    public void deleteWorld() {
        Optional<World> worldToDelete = listing(service().getWorlds()).choose();
        if (worldToDelete.isPresent()) {
            service().removeWorld(worldToDelete.get());
        }
    }

    @Command(name = "create", abbrev = "new", description = "Create new world model")
    public void createWorld(@Param(name = "world name", description = "name of new world model") String name) {
        World world = new World(name);
        service().addWorld(world);
    }

    @Command(name = "edit", abbrev = "e", description = "Edit existing world model")
    public void editWorld() {
        Optional<World> worldToEdit = listing(service().getWorlds()).choose();
        if (worldToEdit.isPresent()) {
            editWorld(worldToEdit.get());
        }
    }

    private void editWorld(World world) {
        runSpecializedShell(new WorldEditShell(service(), world));
    }
}
