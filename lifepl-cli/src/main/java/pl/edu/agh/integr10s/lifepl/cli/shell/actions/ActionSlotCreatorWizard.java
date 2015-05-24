package pl.edu.agh.integr10s.lifepl.cli.shell.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.integr10s.lifepl.model.world.ActionSlotBuilder;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestriction;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestrictionCreator;
import pl.edu.agh.integr10s.lifepl.model.world.restrictions.SlotRestrictionsFabric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

class ActionSlotCreatorWizard {
    private static final Logger logger = LoggerFactory.getLogger(ActionSlotCreatorWizard.class);

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private final SlotRestrictionsFabric restrictionsFabric;

    private static class CancelCreationException extends Exception {
        public CancelCreationException() {
            super("Creation of action slot canceled");
        }
    }

    ActionSlotCreatorWizard(SlotRestrictionsFabric restrictionsFabric) {
        this.restrictionsFabric = restrictionsFabric;
    }

    private LocalDateTime readDateTime(String timePointName) throws CancelCreationException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line;
            System.out.print("please provide value for " + timePointName + " in format \"" + DATE_TIME_FORMAT + "\": ");

            while ((line = in.readLine()) != null) {
                try {
                    LocalDateTime time = LocalDateTime.parse(line, FORMATTER);
                    return time;
                } catch (DateTimeParseException e) {
                    System.out.print("WRONG FORMAT, please provide value for " + timePointName + " in format \"" + DATE_TIME_FORMAT + "\": ");
                }
            }

            throw new CancelCreationException();

        } catch (IOException e) {
            logger.error("error while reading date time for " + timePointName, e);
            throw new CancelCreationException();
        }
    }

    private boolean checkAnswer(String msg) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print(msg + " (y/n) : ");
            String line;
            while ((line = in.readLine()) != null) {
                if(line.equals("y")) return true;
                if (line.equals("n")) return false;

                System.out.print(msg + " (y/n) : ");
            }

            return false;

        } catch (IOException e) {
            logger.error("Error while parsing yes no answer", e);
            return false;
        }
    }



    private Optional<SlotRestriction> readNewSlotRestriction(SlotRestrictionCreator slotRestrictionCreator) {
        boolean ans = checkAnswer("add slot restriction [ " + slotRestrictionCreator.getRestrictionDescription() + " ] to new slot");
        if (ans) {
            return parseSlotRestriction(slotRestrictionCreator);
        } else {
            return Optional.empty();
        }
    }

    private Optional<SlotRestriction> parseSlotRestriction(SlotRestrictionCreator slotRestrictionCreator) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("provide slot restriction value in format " + slotRestrictionCreator.getRestrictionInputFormat() + " : ");

            String line;
            while ((line = in.readLine()) != null) {
                if (slotRestrictionCreator.validateInput(line)) {
                    return Optional.of(slotRestrictionCreator.parseRestriction(line));
                }
                System.out.print("WRONG FORMAT, provide slot restriction value in format " + slotRestrictionCreator.getRestrictionInputFormat() + " : ");
            }

            return Optional.empty();
        } catch (IOException e) {
            logger.error("error during parsing slot restriction", e);
            return Optional.empty();
        }
    }

    private Collection<SlotRestriction> readSlotRestrictions() {
        List<SlotRestriction> restrictionList = new LinkedList<>();
        logger.info("read slot restrictions");

        for (SlotRestrictionCreator slotRestrictionCreator : restrictionsFabric.getRestrictionCreators()) {
            Optional<SlotRestriction> restriction = readNewSlotRestriction(slotRestrictionCreator);
            if (restriction.isPresent()) {
                logger.debug("adding slot restriction {}", restriction.get());
                restrictionList.add(restriction.get());
            }
        }

        return restrictionList;
    }

    public Optional<ActionSlotBuilder> newSlotBuilder() {
        try {
            ActionSlotBuilder builder = new ActionSlotBuilder();

            builder.setSlotStart(readDateTime("slot begin time"));
            builder.setSlotEnd(readDateTime("slot end time"));

            for (SlotRestriction slotRestriction : readSlotRestrictions()) {
                builder.addSlotRestriction(slotRestriction);
            }

            return Optional.of(builder);

        } catch (CancelCreationException e) {
            return Optional.empty();
        }
    }
}
