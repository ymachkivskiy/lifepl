/* Copyright 2013 Sabre Holdings */
package pl.edu.agh.integr.goal;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class GoalDefinitionBuilder {
  public abstract GoalDefinition build();

  protected abstract GoalDefinitionBuilder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions);

  public abstract GoalDefinitionBuilder addAction(Action action);

  public PredecessorActionList withPredecessors() {
    return new PredecessorActionList();
  }

  public ActionList withPredecessor(Action action) {
    return new ActionList(new PredecessorActionList().addAction(action));
  }

  public class PredecessorActionList {
    private Set<Action> predecessors = new HashSet<Action>();

    public PredecessorActionList addAction(Action action) {
      predecessors.add(action);
      return this;
    }
    public ActionList forActions() {
      return new ActionList(this);
    }

    public GoalDefinitionBuilder forAction(Action action) {
      return setUpPredecessors(predecessors, Sets.newHashSet(action));
    }

    Set<Action> getPredecessors() {
      return Collections.unmodifiableSet(predecessors);
    }
  }

  public class ActionList {
    private final Set<Action> predecessors;
    private final Set<Action> actions = new HashSet<Action>();

    public ActionList(PredecessorActionList predecessorActionList) {
      this.predecessors = predecessorActionList.getPredecessors();
    }

    public ActionList addAction(Action action) {
      actions.add(action);
      return this;
    }

    public GoalDefinitionBuilder accept() {
      return setUpPredecessors(predecessors, actions);
    }
  }

}
