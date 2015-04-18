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

  public static abstract class Builder {
    public abstract GoalDefinition build();

    public abstract Builder addAction(Action action);

    public PredecessorActionList withPredecessors() {
      return new PredecessorActionList();
    }

    public class PredecessorActionList {
      public PredecessorActionList addAction(Action action) {
        //TODO
        return this;
      }
      public ActionList forActions() {
        return new ActionList();
      }
    }

    public class ActionList {
      public ActionList addAction(Action action) {
        //TODO Yarek : implement
        return ActionList.this;
      }

      public Builder accept() {
        //TODO
        return Builder.this;
      }
    }

  }

  static Builder DependentActionsGoalBuilder() {
    return DAGActionsGoalDefinition.Builder();
  }

  static Builder IndependentActionsGoalBuilder() {
    return SetActionsGoalDefinition.Builder();
  }

  public static Builder Builder(GoalActionDependency goalActionDependency) {
    return goalActionDependency.getBuilder();
  }
}
