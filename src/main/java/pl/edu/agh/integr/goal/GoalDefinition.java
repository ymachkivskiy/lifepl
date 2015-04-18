package pl.edu.agh.integr.goal;

import java.util.Iterator;

/**
 * Cel który si? sk?ada z akcji.
 */
public abstract class GoalDefinition implements Iterable<Action> {

  public abstract Iterator<Action> iterator();

  public GoalCompletionTracker startWorkingOn() {
    return new GoalCompletionTracker(this);
  }

  static GoalDefinitionBuilder DependentActionsGoalBuilder() {
    return DAGActionsGoalDefinition.Builder();
  }

  static GoalDefinitionBuilder IndependentActionsGoalBuilder() {
    return SetActionsGoalDefinition.Builder();
  }

  public static GoalDefinitionBuilder Builder(GoalActionDependency goalActionDependency) {
    return goalActionDependency.getBuilder();
  }
}
