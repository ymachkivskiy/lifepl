package pl.edu.agh.integr10s.lifepl.cli.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.cli.shell.app.MainShell;

import java.io.IOException;
import java.util.*;

public final class ApplicationShell {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationShell.class);

    private static final String APP_NAME = "Lifepl - application for integration of plans on fixed world model";

    private final SubShell rootLevel;

    private ApplicationShell(SubShell rootLevel) {
        this.rootLevel = rootLevel;
    }


    public void start() {
        try {
            this.rootLevel.runLevel();
        } catch (IOException e) {
            logger.error("error during bootstrapping application shell", e);
            System.exit(1);
        }
    }

    private static class AppShellBuilder {
        private final Set<SubShell> subShells = new HashSet<>();
        private final String applicationName;

        public AppShellBuilder(String applicationName) {
            this.applicationName = applicationName;
        }

        public AppShellBuilder addSubShell(SubShell level) {
            level.setApplicationName(applicationName);
            this.subShells.add(level);
            return this;
        }

        public ApplicationShell build() {

            logger.debug("start building application shell");

            EnumSet<SubShellName> declaredNames = EnumSet.noneOf(SubShellName.class);
            List<SubShell> mainCategoryShells = new LinkedList<>();
            Map<SubShellName, List<SubShell>> parentSubShellNameToSubShellsMap = new HashMap<>();

            for (SubShellName subShellName : SubShellName.values()) {
                parentSubShellNameToSubShellsMap.put(subShellName, new LinkedList<>());
            }

            for (SubShell subShell : subShells) {
                final SubShellName subShellName = subShell.getSubShellName();
                final SubShellName parentSubShellName = subShell.getParentSubShellName();

                logger.debug("found sub shell {} with name {} and parent sub shell {}", subShell, subShellName, parentSubShellName);

                declaredNames.add(subShellName);

                final List<SubShell> subShellNameImplementations = parentSubShellNameToSubShellsMap.get(parentSubShellName);
                if (subShellNameImplementations.size() > 0) {
                    logger.warn("found more than one sub shell implementation {} for name {}", subShell, subShellName);
                }

                subShellNameImplementations.add(subShell);

                if (subShellName == SubShellName.MAIN) {
                    logger.debug("found root sub shell {} ", subShell);
                    mainCategoryShells.add(subShell);
                }
            }

            logger.debug("checking dependencies of declared sub shells");

            if (mainCategoryShells.isEmpty()) {
                logger.error("no root level sub shell found during building application shell");
                System.exit(1);
            } else if (mainCategoryShells.size() > 1) {
                logger.warn("found more than one sub shell of root level, will peek first found as root");
            }

            SubShell rootLevelElement = mainCategoryShells.get(0);

            logger.debug("use {} sub shell as application shell root", rootLevelElement);

            Stack<SubShell> unconfiguredSubShells = new Stack<>();

            unconfiguredSubShells.push(rootLevelElement);

            while (!unconfiguredSubShells.isEmpty()) {
                SubShell currentSubShell = unconfiguredSubShells.pop();
                logger.debug("configuring dependencies for sub shell {}", currentSubShell);
                List<SubShell> childSubShells = parentSubShellNameToSubShellsMap.get(currentSubShell.getSubShellName());
                if (childSubShells.isEmpty()) {
                    logger.debug("sub shell {} has no child sub shells", currentSubShell);
                }

                for (SubShell childSubShell : childSubShells) {
                    logger.debug("found child sub shell {}", childSubShell);
                    currentSubShell.addChildSubShell(childSubShell);
                    unconfiguredSubShells.push(childSubShell);
                }
            }

            return new ApplicationShell(rootLevelElement);
        }
    }

    private static ApplicationShell createShell() {
        return new AppShellBuilder(APP_NAME)
                .addSubShell(new MainShell())
                .build();
    }


    public static void startApp() throws IOException {
        createShell().start();
    }
}
