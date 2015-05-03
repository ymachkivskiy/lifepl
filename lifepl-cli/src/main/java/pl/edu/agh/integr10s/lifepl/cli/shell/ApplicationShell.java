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

        private static final class BuildEngine {

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

            public List<SubShell> getMainCategoryShells() {
                return mainCategoryShells;
            }
        }

        public AppShellBuilder(String applicationName) {
            this.applicationName = applicationName;
        }

        public AppShellBuilder addSubShell(SubShell level) {
            level.setApplicationName(applicationName);
            this.subShells.add(level);
            return this;
        }

        public ApplicationShell build() {
            BuildEngine buildEngine = new BuildEngine();

            logger.debug("start building application shell");

            buildEngine.processSubShells(subShells);

            logger.debug("checking dependencies of declared sub shells");

            SubShell rootSubShell = buildEngine.peekRootSubShell();

            logger.debug("use  ' {} '  sub shell as application shell root", rootSubShell);

            buildEngine.injectSubShellsDependencies(rootSubShell);

            logger.debug("finish building application shell");

            return new ApplicationShell(rootSubShell);
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
