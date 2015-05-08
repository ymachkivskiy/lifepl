package pl.edu.agh.integr10s.clibuilder.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;
import pl.edu.agh.integr10s.clibuilder.shell.SubShell;

import java.util.*;

final class BuildEngine<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> {
    private static final Logger logger = LoggerFactory.getLogger(BuildEngine.class);

    List<SubShell<E, AppStateT>> mainCategoryShells = new LinkedList<>();
    Map<E, List<SubShell<E, AppStateT>>> parentSubShellNameToSubShellsMap = new HashMap<>();

    public BuildEngine(Class<E> subShellNamesClass) {
        for (E shellNameAware : subShellNamesClass.getEnumConstants()) {
            parentSubShellNameToSubShellsMap.put(shellNameAware, new LinkedList<>());
        }
    }

    public void processSubShells(Set<SubShell<E, AppStateT>> subShells) {
        for (SubShell<E, AppStateT> subShell : subShells) {
            final ShellNameAware<E> shellNameAware = subShell.getSubShellName();
            final ShellNameAware<E> parentSubShellName = subShell.getParentSubShellName();

            logger.debug("has declaration of sub shell  ' {} '  with name  ' {} '  and parent sub shell  ' {} ' ", subShell, shellNameAware, parentSubShellName);

            parentSubShellNameToSubShellsMap.get(parentSubShellName).add(subShell);

            if (shellNameAware.isMain()) {
                logger.debug("found root sub shell  ' {} '  ", subShell);
                mainCategoryShells.add(subShell);
            }
        }
    }

    public void injectSubShellsDependencies(SubShell<E, AppStateT> rootLevelElement) {

        Stack<SubShell<E, AppStateT>> unconfiguredSubShells = new Stack<>();

        unconfiguredSubShells.push(rootLevelElement);

        while (!unconfiguredSubShells.isEmpty()) {
            SubShell<E, AppStateT> currentSubShell = unconfiguredSubShells.pop();
            logger.debug("configuring dependencies for sub shell  ' {} ' ", currentSubShell);
            List<SubShell<E, AppStateT>> childSubShells = parentSubShellNameToSubShellsMap.get(currentSubShell.getSubShellName());
            if (childSubShells.isEmpty()) {
                logger.debug("sub shell  ' {} '  has no child sub shells", currentSubShell);
            }

            for (SubShell<E, AppStateT> childSubShell : childSubShells) {
                logger.debug("found child sub shell  ' {} ' ", childSubShell);
                currentSubShell.addChildSubShell(childSubShell);
                unconfiguredSubShells.push(childSubShell);
            }
        }
    }


    public SubShell<E, AppStateT> peekRootSubShell() {
        if (mainCategoryShells.isEmpty()) {
            logger.error("no root level sub shell found during building application shell");
            System.exit(1);
        } else if (mainCategoryShells.size() > 1) {
            logger.warn("found more than one sub shell of root level, will peek first found as root");
        }
        return mainCategoryShells.get(0);
    }

}
