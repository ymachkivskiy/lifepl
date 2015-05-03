package pl.edu.agh.integr10s.lifepl.cli.shell.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShell;
import pl.edu.agh.integr10s.lifepl.cli.shell.SubShellName;

import java.util.*;

final class BuildEngine {
    private static final Logger logger = LoggerFactory.getLogger(BuildEngine.class);

    List<SubShell> mainCategoryShells = new LinkedList<>();
    Map<SubShellName, List<SubShell>> parentSubShellNameToSubShellsMap = new HashMap<>();

    public BuildEngine() {
        for (SubShellName subShellName : SubShellName.values()) {
            parentSubShellNameToSubShellsMap.put(subShellName, new LinkedList<>());
        }
    }

    public void processSubShells(Set<SubShell> subShells) {
        for (SubShell subShell : subShells) {
            final SubShellName subShellName = subShell.getSubShellName();
            final SubShellName parentSubShellName = subShell.getParentSubShellName();

            logger.debug("has declaration of sub shell  ' {} '  with name  ' {} '  and parent sub shell  ' {} ' ", subShell, subShellName, parentSubShellName);

            parentSubShellNameToSubShellsMap.get(parentSubShellName).add(subShell);

            if (subShellName == SubShellName.MAIN) {
                logger.debug("found root sub shell  ' {} '  ", subShell);
                mainCategoryShells.add(subShell);
            }
        }
    }

    public void injectSubShellsDependencies(SubShell rootLevelElement) {

        Stack<SubShell> unconfiguredSubShells = new Stack<>();

        unconfiguredSubShells.push(rootLevelElement);

        while (!unconfiguredSubShells.isEmpty()) {
            SubShell currentSubShell = unconfiguredSubShells.pop();
            logger.debug("configuring dependencies for sub shell  ' {} ' ", currentSubShell);
            List<SubShell> childSubShells = parentSubShellNameToSubShellsMap.get(currentSubShell.getSubShellName());
            if (childSubShells.isEmpty()) {
                logger.debug("sub shell  ' {} '  has no child sub shells", currentSubShell);
            }

            for (SubShell childSubShell : childSubShells) {
                logger.debug("found child sub shell  ' {} ' ", childSubShell);
                currentSubShell.addChildSubShell(childSubShell);
                unconfiguredSubShells.push(childSubShell);
            }
        }
    }


    public SubShell peekRootSubShell() {
        if (mainCategoryShells.isEmpty()) {
            logger.error("no root level sub shell found during building application shell");
            System.exit(1);
        } else if (mainCategoryShells.size() > 1) {
            logger.warn("found more than one sub shell of root level, will peek first found as root");
        }
        return mainCategoryShells.get(0);
    }

}
