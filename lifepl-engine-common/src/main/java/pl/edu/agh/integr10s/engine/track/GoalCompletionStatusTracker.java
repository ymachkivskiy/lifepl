package pl.edu.agh.integr10s.engine.track;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.actor.Goal;
import pl.edu.agh.integr10s.lifepl.model.util.IdempotentFunction;
import pl.edu.agh.integr10s.lifepl.model.util.graph.DependencyGraph;
import pl.edu.agh.integr10s.lifepl.model.world.Action;

import java.util.*;

/**
 * NOT THREAD SAFE
 * <p/>
 * Monitor stanu spelnienia celu, jest tworzony na podstawie GoalDefinition,
 * dla ktorego na poczatku wszystkie zadania nie sa spelnione.
 */
public class GoalCompletionStatusTracker {

    private final static Logger logger = LoggerFactory.getLogger(GoalCompletionStatusTracker.class);

    private final ActionToTaskStatusMapper actionMapper = new ActionToTaskStatusMapper();
    private final DependencyGraph<ActionStatus> taskDependencyGraph;
    private final Set<Action> notDoneActionsSet;

    public GoalCompletionStatusTracker(Goal goal) {
        final DependencyGraph<Action> actionDependencyGraph = goal.getActionDependencyGraph();
        notDoneActionsSet = Sets.newHashSet(actionDependencyGraph.getElements());
        taskDependencyGraph = actionDependencyGraph.translateSavingDependencies(actionMapper);
    }

    /**
     * Zwraca informacje, czy dany cel zostal w pelni osiagniety.
     *
     * @return osiagniecie celu
     */
    public boolean goalReached() {
        return notDoneActionsSet.isEmpty();
    }

    /**
     * Zwraca kolekcje zadan zawartych w tym celu. Zadania sa uporzadkowane wedlug
     * zaleznosci, tzn. ze jesli B zalezy od A, to a pojawi sie w kolekcji wczesniej.
     *
     * @return kolekcja zawartychw srodku zadan
     */
    public Set<ActionStatus> getActionsStatuses() {
        return taskDependencyGraph.getElements();
    }

    /**
     * Zwraca status zadania dla podanej akcji jesli taka nalezy do celu, inaczej Optional.empty()
     *
     * @param action akcja dla ktorej zwracamy stan zadania
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
                if (!masterActionStatus.isDone()) {
                    logger.warn("action {} on which action {} depends is not done", masterActionStatus.getAction(), action);
                    return false;
                }
            }

            logger.info("marking action {} as done", action);
            status.markDone();

            notDoneActionsSet.remove(action);

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
            notDoneActionsSet.add(action);

            for (ActionStatus dependentActionStatus : taskDependencyGraph.getDependentElementsFor(status)) {
                logger.info("marking dependent action {} as not done", dependentActionStatus.getAction());
                dependentActionStatus.markUnDone();
                notDoneActionsSet.add(dependentActionStatus.getAction());
            }

            return true;
        } else {
            logger.warn("try to mark action {} which is not tracked by this tracker", action);
            return false;
        }
    }

    private Set<Action> filterUndoneActions(Set<ActionStatus> actionsStatuses) {
        Set<Action> undoneActions = new HashSet<>();
        for (ActionStatus actionStatus : actionsStatuses) {
            if (!actionStatus.isDone()) {
                undoneActions.add(actionStatus.getAction());
            }
        }
        return undoneActions;
    }
    /**
     * Sluzy do pobierania zbioru nie wykonanych akcji, ktore moga aktualnie zostac wykonane,
     * w sensie wszystkie akcje, od ktorych sa zalezne zostaly wykonane, lub akcje nie maja zaleznosci.
     *
     * @return zbior nie wykonanych akcji ktore aktualnie mozna wykonac
     */
    public Set<Action> getNonBlockedUndoneActions() {
        logger.info("getting non blocked undone actions");

        if (notDoneActionsSet.isEmpty()) {
            logger.info("there is no undone actions");
            return Collections.emptySet();
        }

        Set<Action> nonBlockedUndoneActions = new HashSet<>();
        Set<Action> workingCopyOfNotDoneActions = new HashSet<>(notDoneActionsSet);

        for (Iterator<Action> $undoneAction = workingCopyOfNotDoneActions.iterator(); $undoneAction.hasNext(); ) {
            Action undoneAction = $undoneAction.next();
            logger.debug("checking undone action {} and its dependencies", undoneAction);

            Optional<ActionStatus> undoneActionStatus = getStatusForAction(undoneAction);
            if (undoneActionStatus.isPresent()) {
                final Set<ActionStatus> actionsStatusesOnWhichCurrentActionDepends = taskDependencyGraph.getElementsOnWhichElementDepends(undoneActionStatus.get());
                final Set<Action> undoneActionsOnWhichCurrentActionDepends = filterUndoneActions(actionsStatusesOnWhichCurrentActionDepends);

                if (undoneActionsOnWhichCurrentActionDepends.isEmpty()) {
                    logger.info("adding action {} to set of unblocked undone actions", undoneAction);
                } else {
                    logger.debug("action {} has undone actions on which it depends on : {}", undoneAction, undoneActionsOnWhichCurrentActionDepends);
                }

            } else {
                logger.error("action {} not found in status tracker during performing getNonBlockedUndoneActions()", undoneAction);
                return Collections.emptySet();
            }
        }

        return nonBlockedUndoneActions;
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
