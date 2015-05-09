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
        GoalDefinition.GoalDefinitionBuilder builder = GoalDefinition.NewBuilder();
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
                .addIndependentElement(A[0])
                .withPredecessor(A[1])
                .forElements()
                .addElement(A[2])
                .addElement(A[3])
                .addElement(A[4])
                .accept()
                .withPredecessors()
                .addElement(A[3])
                .addElement(A[4])
                .forElement(A[5])
                .withPredecessors()
                .addElement(A[2])
                .addElement(A[5])
                .forElement(A[6])
                .withPredecessors()
                .addElement(A[6])
                .addElement(A[7])
                .forElement(A[8])
                .withPredecessor(A[8])
                .forElement(A[9])
        ;

        tested = builder.buildGoalDefinition();
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
