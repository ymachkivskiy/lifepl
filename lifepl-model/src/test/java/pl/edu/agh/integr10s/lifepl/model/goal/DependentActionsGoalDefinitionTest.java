package pl.edu.agh.integr10s.lifepl.model.goal;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DependentActionsGoalDefinitionTest extends GoalDefinitionTest {

  public static final int ACTIONS_COUNT = 10;
  public static final Action[] A = new Action[ACTIONS_COUNT];

  @Before
  public void setUp() {
    GoalDefinitionBuilder builder = GoalDefinition.Builder(GoalActionDependency.DEPENDENT);
/*

                       +----+
               +------->a2  +---------------------+          +----+
               |       +----+                     +---------->a6  +------------+
               |                                             +-^--+            |
               |                                               |               |
      +---+    |                                               |               |
      |a0 |    |                                               |             +-v--+                  +----+
      +---+    |       +----+            +----+                |             |a8  +------------------>a9  |
               +------->a3  +------------>a5  +----------------+             +-^--+                  +----+
               |       +----+            +--^-+                |               |
      +---+    |                            |                  |               |
      |a1 +----+                            |                 +v---+           |
      +---+    |                            |                 |a7  +-----------+
               |                            |                 +----+
               |       +----+               |
               +------->a4  +---------------+
                       +----+
 */
    for (int i = 0; i < ACTIONS_COUNT; i++) {
      A[i] = createRandomAction();
    }

    builder
      .addAction(A[0])
      .withPredecessor(A[1])
        .forActions()
          .addAction(A[2])
          .addAction(A[3])
          .addAction(A[4])
      .accept()
      .withPredecessors()
          .addAction(A[3])
          .addAction(A[4])
        .forAction(A[5])
      .withPredecessors()
          .addAction(A[2])
          .addAction(A[5])
        .forAction(A[6])
      .withPredecessors()
          .addAction(A[6])
          .addAction(A[7])
        .forAction(A[8])
      .withPredecessor(A[8])
      .forAction(A[9])
      ;

    tested = builder.build();
  }

  @Test
  public void testOrder() throws Exception {

    ArrayList actions = Lists.newArrayList(tested);

    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[2]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[3]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[4]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[5]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[6]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[7]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[1]) < actions.indexOf(A[9]));

    assertTrue(actions.indexOf(A[2]) < actions.indexOf(A[6]));
    assertTrue(actions.indexOf(A[2]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[2]) < actions.indexOf(A[9]));

    assertTrue(actions.indexOf(A[3]) < actions.indexOf(A[5]));
    assertTrue(actions.indexOf(A[3]) < actions.indexOf(A[6]));
    assertTrue(actions.indexOf(A[3]) < actions.indexOf(A[7]));
    assertTrue(actions.indexOf(A[3]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[3]) < actions.indexOf(A[9]));


    assertTrue(actions.indexOf(A[4]) < actions.indexOf(A[5]));
    assertTrue(actions.indexOf(A[4]) < actions.indexOf(A[6]));
    assertTrue(actions.indexOf(A[4]) < actions.indexOf(A[7]));
    assertTrue(actions.indexOf(A[4]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[4]) < actions.indexOf(A[9]));


    assertTrue(actions.indexOf(A[5]) < actions.indexOf(A[6]));
    assertTrue(actions.indexOf(A[5]) < actions.indexOf(A[7]));
    assertTrue(actions.indexOf(A[5]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[5]) < actions.indexOf(A[9]));


    assertTrue(actions.indexOf(A[6]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[6]) < actions.indexOf(A[9]));


    assertTrue(actions.indexOf(A[7]) < actions.indexOf(A[8]));
    assertTrue(actions.indexOf(A[7]) < actions.indexOf(A[9]));

    assertTrue(actions.indexOf(A[8]) < actions.indexOf(A[9]));

  }

}
