package pl.edu.agh.integr10s.lifepl.model.actor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.util.graph.DependencyGraph;
import pl.edu.agh.integr10s.lifepl.model.util.graph.DependencyGraphBuilder;
import pl.edu.agh.integr10s.lifepl.model.world.Action;

import java.util.Iterator;

/**
 * Cel ktory sie sklada z akcji
 */
public class Goal implements Iterable<Action> {

    private static final Logger logger = LoggerFactory.getLogger(Goal.class);

    private final DependencyGraph<Action> actionDependencyGraph;

    Goal(DependencyGraph<Action> actionDependencyGraph) {
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

        public Goal buildGoalDefinition() {
            return new Goal(build());
        }

    }

}
