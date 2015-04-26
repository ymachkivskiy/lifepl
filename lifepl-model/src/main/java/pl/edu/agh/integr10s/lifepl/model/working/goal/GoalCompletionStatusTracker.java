package pl.edu.agh.integr10s.lifepl.model.working.goal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.definition.goal.Action;
import pl.edu.agh.integr10s.lifepl.model.definition.goal.GoalDefinition;
import pl.edu.agh.integr10s.lifepl.model.graph.model.DependencyGraph;
import pl.edu.agh.integr10s.lifepl.model.graph.model.IdempotentFunction;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * NOT THREAD SAFE
 *
 * Monitor stanu spelnienia celu, jest tworzony na podstawie GoalDefinition,
 * dla ktorego na poczatku wszystkie zadania nie sa spelnione.
 */
public class GoalCompletionStatusTracker {

    private final static Logger logger = LoggerFactory.getLogger(GoalCompletionStatusTracker.class);

    private final ActionToTaskStatusMapper actionMapper = new ActionToTaskStatusMapper();
    private final DependencyGraph<ActionStatus> taskDependencyGraph;
    private int doneActions = 0;

    public GoalCompletionStatusTracker(GoalDefinition goalDefinition) {
        taskDependencyGraph = goalDefinition.getActionDependencyGraph().translateSavingDependencies(actionMapper);
    }

    /**
     * Zwraca informacje, czy dany cel zostal w pelni osiagniety.
     *
     * @return osiagniecie celu
     */
    public boolean goalReached() {
        return doneActions == taskDependencyGraph.getSize();
    }

    /**
     * Zwraca kolekcje zadan zawartych w tym celu. Zadania sa uporzadkowane wedlug
     * zaleznosci, tzn. ze jesli B zalezy od A, to a pojawi sie w kolekcji wczesniej.
     *
     * @return kolekcja zawartychw srodku zadan
     */
    public Set<ActionStatus> getTasks() {
        return taskDependencyGraph.getElements();
    }

    /**
     * Zwraca status zadania dla podanej akcji jesli taka nalezy do celu, inaczej Optional.empty()
     *
     * @param action akcja dla kt�rej zwracamy stan zadania
     * @return stan zadania
     */
    public Optional<ActionStatus> getStatusForAction(Action action) {
        return actionMapper.getTaskStatusForAction(action);
    }

    /**
     * Metoda ktora zazwnacza podana skcje (jesli taka nalezy do celu) jako wykonana tylko
     * w przypadku jesli akcje od ktorych ona jest zalezna zostaly wykonane, czyli sa done.
     *
     * @param action akcja ktora probujemy
     * @return jesli akcja zostala oznaczona jako wykonana true,  w przeciwnym przypadku false
     */
    public boolean markAsDone(final Action action) {
        if (action == null) {
            logger.error("try to mark null action as done");
            return false;
        }

        Optional<ActionStatus> actionStatus = actionMapper.getTaskStatusForAction(action);
        if (actionStatus.isPresent()) {
            final ActionStatus status = actionStatus.get();
            if (status.isDone()) {
                logger.warn("try to mark action {} as done, but it is already marked as done", action);
                return true;
            }

            logger.info("check if actions on which action {} depends are done", action);

            for (ActionStatus masterActionStatus : taskDependencyGraph.getElementsOnWhichElementDepends(status)) {
                if(!masterActionStatus.isDone()) {
                    logger.warn("action {} on which action {} depends is not done", masterActionStatus.getAction(), action);
                    return false;
                }
            }

            logger.info("marking action {} as done", action);
            status.markDone();

            return true;
        } else {
            logger.warn("try to mark action {} which is not tracked by this tracker", action);
            return false;
        }
    }

    /**
     * Metoda ktora zaznacza akcje oraz wzystkie od niej zalezne akcje jako nie zrobione.
     *
     * @param action akcja ktora trzeba oznaczyc jako nie zrobiona
     * @return jesli akcja zostala zaznaczona jako nie wykonana true, w przeciwnym przypadku false
     */
    public boolean markAsNotDone(final Action action) {
        if (action == null) {
            logger.error("try to mark null action as not done");
            return false;
        }

        Optional<ActionStatus> actionStatus = actionMapper.getTaskStatusForAction(action);
        if (actionStatus.isPresent()) {

            final ActionStatus status = actionStatus.get();
            if (!status.isDone()) {
                logger.warn("try to mark action {} as not done, but it is already marked as not done", action);
                return true;
            }

            logger.info("marking action {} as not done", action);
            status.markUnDone();

            for (ActionStatus dependentActionStatus : taskDependencyGraph.getDependentElementsFor(status)) {
                logger.info("marking dependent action {} as not done", dependentActionStatus.getAction());
                dependentActionStatus.markUnDone();
            }

            return true;
        } else {
            logger.warn("try to mark action {} which is not tracked by this tracker", action);
            return false;
        }
    }

    /**
     * Sluzy do pobierania zbioru nie wykonanych akcji, ktore moga aktualnie zostac wykonane,
     * w sensie wszystkie akcje, od ktorych sa zalezne zostaly wykonane, lub akcje nie maja zaleznosci.
     *
     * @return zbior nie wykonanych akcji ktore aktualnie mozna wykonac
     */
    public Set<Action> getTasksCanBePerformedCurrently() {
        //TODO Yarek : implement
        return Collections.emptySet();
    }

    private static class ActionToTaskStatusMapper extends IdempotentFunction<Action, ActionStatus> {

        public Optional<ActionStatus> getTaskStatusForAction(Action action) {
            return getStoredResultForArgument(action);
        }

        @Override
        protected ActionStatus apply(Action argument) {
            return new ActionStatus(argument);
        }

    }
}
