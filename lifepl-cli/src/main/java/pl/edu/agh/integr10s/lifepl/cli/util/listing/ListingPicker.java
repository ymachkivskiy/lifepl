package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import org.apache.commons.lang.math.IntRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class ListingPicker<T> {

    private static final Logger logger = LoggerFactory.getLogger(ListingPicker.class);

    public Collection<T> peekMany(ListingDataModel<T> model) {
        if (model.getRowCount() == 0) {
            logger.info("data model is empty, nothing to choose from");
            return Collections.emptyList();
        }

        IntRange choseRange = new IntRange(1, model.getRowCount());
        final String prompt = "chose number inside " + choseRange.toString() + " separated with ',' : ";

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            readPromptLine:
            while (true) {
                System.out.print(prompt);
                String line = in.readLine();

                if (line == null) {
                    break;
                }

                String numbers[] = line.split(",");

                try {
                    Set<Integer> chosenItems = new HashSet<>();

                    for (String number : numbers) {
                        Integer chosen = Integer.parseInt(number);
                        if (!choseRange.containsInteger(chosen)) {
                            logger.info("provided number {} is out of permitted range {} ", chosen, choseRange);
                            continue readPromptLine;
                        } else {
                            chosenItems.add(chosen);
                        }
                    }

                    ArrayList<T> resultList = new ArrayList<>(chosenItems.size());
                    for (Integer chosenItem : chosenItems) {
                        resultList.add(model.getElement(chosenItem));
                    }

                    return resultList;
                } catch (NumberFormatException e) {
                    logger.info("invalid number format, input must be integer number, but was \"{}\" ", line);
                }
            }

        } catch (IOException e) {
            logger.error("console reader error ", e);
        }

        return Collections.emptyList();
    }

    public Optional<T> peekOne(ListingDataModel<T> model) {
        if (model.getRowCount() == 0) {
            logger.info("data model is empty, nothing to choose from");
            return Optional.empty();
        }

        IntRange choseRange = new IntRange(1, model.getRowCount());
        final String prompt = "chose number inside " + choseRange.toString() + " : ";

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print(prompt);
                String line = in.readLine();

                if (line == null) {
                    break;
                }

                try {

                    Integer chosen = Integer.parseInt(line);
                    if (!choseRange.containsInteger(chosen)) {
                        logger.info("provided number {} is out of permitted range {} ", chosen, choseRange);
                        continue;
                    }

                    return Optional.of(model.getElement(chosen));
                } catch (NumberFormatException e) {
                    logger.info("invalid number format, input must be integer number, but was \"{}\" ", line);
                }
            }

        } catch (IOException e) {
            logger.error("console reader error ", e);
        }

        return Optional.empty();
    }
}
