package pl.edu.agh.integr10s.lifepl.model.definition.goal;

import com.google.common.collect.Sets;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class GoalDefinitionBuilder {
  private static final Logger logger = Logger.getLogger(GoalDefinitionBuilder.class);

  public abstract GoalDefinition build();

  protected abstract GoalDefinitionBuilder setUpPredecessors(Set<Action> currentActionsPredecessors, Set<Action> currentActions);

  public abstract GoalDefinitionBuilder addAction(Action action);

  public PredecessorActionList withPredecessors() {
    logger.info("start to construct list of predecessor actions");
    return new PredecessorActionList(this);
  }

  public PredecessorsListToActionListBridge withPredecessor(Action action) {
    logger.info("constructed list of predecessor actions with one action : " + action);
    return new PredecessorsListToActionListBridge(this, new PredecessorActionList(this).addAction(action));
  }

  public static class PredecessorsListToActionListBridge {

    private final GoalDefinitionBuilder builder;
    private final PredecessorActionList predecessorActionList;

    public PredecessorsListToActionListBridge(GoalDefinitionBuilder builder, PredecessorActionList predecessorActionList) {
      this.builder = builder;
      this.predecessorActionList = predecessorActionList;
    }

    public ActionList forActions() {
      logger.info("start to construct list of actions");
      return new ActionList(builder, predecessorActionList);
    }

    public GoalDefinitionBuilder forAction(Action action) {
      logger.info("constructed list of actions with one action : " + action);
      return builder.setUpPredecessors(predecessorActionList.getPredecessors(), Sets.newHashSet(action));
    }
  }

  public static class PredecessorActionList {
    private final GoalDefinitionBuilder builder;
    private Set<Action> predecessors = new HashSet<Action>();

    private PredecessorActionList(GoalDefinitionBuilder builder) {
      this.builder = builder;
    }

    public PredecessorActionList addAction(Action action) {
      logger.info("add action to predecessors actions list : " + action);
      predecessors.add(action);
      return this;
    }

    public ActionList forActions() {
      logger.info("finish to construct predecessors actions list");
      logger.info("start to construct list of actions");
      return new ActionList(builder, this);
    }

    public GoalDefinitionBuilder forAction(Action action) {
      logger.info("applying predecessors list for single action : " + action);
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

    private ActionList(GoalDefinitionBuilder builder, PredecessorActionList predecessorActionList) {
      this.builder = builder;
      this.predecessors = predecessorActionList.getPredecessors();
    }

    public ActionList addAction(Action action) {
      logger.info("add action to action list : " + action);
      actions.add(action);
      return this;
    }

    public GoalDefinitionBuilder accept() {
      logger.info("finish to construct list of actions");
      return builder.setUpPredecessors(predecessors, actions);
    }

  }

}
