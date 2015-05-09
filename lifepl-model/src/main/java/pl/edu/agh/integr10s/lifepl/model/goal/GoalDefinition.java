package pl.edu.agh.integr10s.lifepl.model.goal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.util.graph.DependencyGraph;
import pl.edu.agh.integr10s.lifepl.model.util.graph.DependencyGraphBuilder;

import java.util.Iterator;

/**
 * Cel ktory sie sklada z akcji
 */
public class GoalDefinition implements Iterable<Action> {

    private static final Logger logger = LoggerFactory.getLogger(GoalDefinition.class);

    private final DependencyGraph<Action> actionDependencyGraph;

    GoalDefinition(DependencyGraph<Action> actionDependencyGraph) {
        this.actionDependencyGraph = actionDependencyGraph;
    }

    public static GoalDefinitionBuilder NewBuilder() {
        return new GoalDefinitionBuilder();
    }

    public Iterator<Action> iterator() {
        return actionDependencyGraph.iteratorWithDependentOrder();
    }

    public DependencyGraph<Action> getActionDependencyGraph() {
        return actionDependencyGraph;
    }

    public static final class GoalDefinitionBuilder extends DependencyGraphBuilder<Action> {

        private GoalDefinitionBuilder() {
        }

        public GoalDefinition buildGoalDefinition() {
            return new GoalDefinition(build());
        }

    }

}
