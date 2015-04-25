package pl.edu.agh.integr10s.lifepl.model.definition.goal;


import org.apache.log4j.Logger;
import pl.edu.agh.integr10s.lifepl.model.working.goal.GoalStatusTracker;

import java.util.Iterator;

/**
 * Cel ktory sie sklada z akcji
 */
public abstract class GoalDefinition implements Iterable<Action> {
    private static final Logger logger = Logger.getLogger(GoalDefinition.class);

    static GoalDefinitionBuilder DependentActionsGoalBuilder() {
        return DAGActionsGoalDefinition.Builder();
    }

    static GoalDefinitionBuilder IndependentActionsGoalBuilder() {
        return SetActionsGoalDefinition.Builder();
    }

    public static GoalDefinitionBuilder Builder(GoalActionDependency goalActionDependency) {
        logger.info("getting goal definition builder for " + goalActionDependency + " action dependency type");
        return goalActionDependency.getBuilder();
    }

    public abstract Iterator<Action> iterator();

    public GoalStatusTracker startWorkingOn() {
        return new GoalStatusTracker(this);
    }
}
