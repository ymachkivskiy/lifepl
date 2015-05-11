package pl.edu.agh.integr10s.clibuilder.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.clibuilder.shell.AppContext;
import pl.edu.agh.integr10s.clibuilder.shell.CategorizedShell;
import pl.edu.agh.integr10s.clibuilder.shell.ShellNameAware;

import java.util.*;

final class BuildEngine<E extends Enum<E> & ShellNameAware<E>, AppStateT extends AppContext> {
    private static final Logger logger = LoggerFactory.getLogger(BuildEngine.class);

    List<CategorizedShell<E, AppStateT>> mainCategoryShells = new LinkedList<>();
    Map<E, List<CategorizedShell<E, AppStateT>>> parentSubShellNameToSubShellsMap = new HashMap<>();

    public BuildEngine(Class<E> subShellNamesClass) {
        for (E shellNameAware : subShellNamesClass.getEnumConstants()) {
            parentSubShellNameToSubShellsMap.put(shellNameAware, new LinkedList<>());
        }
    }

    public void processSubShells(Set<CategorizedShell<E, AppStateT>> categorizedShells) {
        for (CategorizedShell<E, AppStateT> categorizedShell : categorizedShells) {
            final ShellNameAware<E> shellNameAware = categorizedShell.getSubShellName();
            final ShellNameAware<E> parentSubShellName = categorizedShell.getParentSubShellName();

            logger.debug("has declaration of sub shell  ' {} '  with name  ' {} '  and parent sub shell  ' {} ' ", categorizedShell, shellNameAware, parentSubShellName);

            parentSubShellNameToSubShellsMap.get(parentSubShellName).add(categorizedShell);

            if (shellNameAware.isMain()) {
                logger.debug("found root sub shell  ' {} '  ", categorizedShell);
                mainCategoryShells.add(categorizedShell);
            }
        }
    }

    public void injectSubShellsDependencies(CategorizedShell<E, AppStateT> rootLevelElement) {

        Stack<CategorizedShell<E, AppStateT>> unconfiguredCategorizedShells = new Stack<>();

        unconfiguredCategorizedShells.push(rootLevelElement);

        while (!unconfiguredCategorizedShells.isEmpty()) {
            CategorizedShell<E, AppStateT> currentCategorizedShell = unconfiguredCategorizedShells.pop();
            logger.debug("configuring dependencies for sub shell  ' {} ' ", currentCategorizedShell);
            List<CategorizedShell<E, AppStateT>> childCategorizedShells = parentSubShellNameToSubShellsMap.get(currentCategorizedShell.getSubShellName());
            if (childCategorizedShells.isEmpty()) {
                logger.debug("sub shell  ' {} '  has no child sub shells", currentCategorizedShell);
            }

            for (CategorizedShell<E, AppStateT> childCategorizedShell : childCategorizedShells) {
                logger.debug("found child sub shell  ' {} ' ", childCategorizedShell);
                currentCategorizedShell.addChildSubShell(childCategorizedShell);
                unconfiguredCategorizedShells.push(childCategorizedShell);
            }
        }
    }


    public CategorizedShell<E, AppStateT> peekRootSubShell() {
        if (mainCategoryShells.isEmpty()) {
            logger.error("no root level sub shell found during building application shell");
            System.exit(1);
        } else if (mainCategoryShells.size() > 1) {
            logger.warn("found more than one sub shell of root level, will peek first found as root");
        }
        return mainCategoryShells.get(0);
    }

}
