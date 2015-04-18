package pl.edu.agh.integr;

import pl.edu.agh.integr.goal.Action;
import pl.edu.agh.integr.goal.GoalActionDependency;
import pl.edu.agh.integr.goal.GoalDefinition;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    final Action  a0 = null;
    final Action a1 = null;
    final Action a2 = null;
    final Action a3 = null;
    final Action a4 = null;

//    GoalDefinition def = GoalDefinition.DependentActionsGoalBuilder()
//      .addAction(a0)
//      .withPredecessors()
//        .setPredessor(a1)
//        .forActions()
//          .addAction(a2)
//          .addAction(a3)
//        .markAdded()

    GoalDefinition def = GoalDefinition.Builder(GoalActionDependency.DEPENDENT)
      .addAction(a0)
      .withPredecessors()
        .addAction(a1)
      .forActions()
        .addAction(a2)
        .addAction(a3)
      .accept()
      .withPredecessors()
        .addAction(a2)
        .addAction(a3)
      .forActions()
        .addAction(a4)
      .accept()
      .build();

  }
}
