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
    return new PredecessorActionList(this);
  }

  public ActionList withPredecessor(Action action) {
    return new ActionList(this, new PredecessorActionList(this).addAction(action));
  }

  public static class PredecessorActionList {
    private final GoalDefinitionBuilder builder;
    private Set<Action> predecessors = new HashSet<Action>();

    public PredecessorActionList(GoalDefinitionBuilder builder) {
      this.builder = builder;
    }

    public PredecessorActionList addAction(Action action) {
      predecessors.add(action);
      return this;
    }
    public ActionList forActions() {
      return new ActionList(builder, this);
    }

    public GoalDefinitionBuilder forAction(Action action) {
      return builder.setUpPredecessors(predecessors, Sets.newHashSet(action));
    }

    Set<Action> getPredecessors() {
      return Collections.unmodifiableSet(predecessors);
    }
  }

  public static class ActionList {
    private final Set<Action> predecessors;
    private final Set<Action> actions = new HashSet<Action>();
    private final GoalDefinitionBuilder builder;

    public ActionList(GoalDefinitionBuilder builder, PredecessorActionList predecessorActionList) {
      this.builder = builder;
      this.predecessors = predecessorActionList.getPredecessors();
    }

    public ActionList addAction(Action action) {
      actions.add(action);
      return this;
    }

    public GoalDefinitionBuilder accept() {
      return builder.setUpPredecessors(predecessors, actions);
    }

    public GoalDefinitionBuilder forAction(Action action) {
      addAction(action);
      return accept();
    }
  }

}
