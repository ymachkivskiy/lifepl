package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import jline.console.ConsoleReader;
import org.apache.commons.lang.math.IntRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

class ListingPicker<T> {

    private static final Logger logger = LoggerFactory.getLogger(ListingPicker.class);

    public Optional<T> peekOne(ListingDataModel<T> model) {
        if (model.getRowCount() == 0) {
            logger.info("data model is empty, nothing to choose from");
            return Optional.empty();
        }

        IntRange choseRange = new IntRange(1, model.getRowCount());

        try {

            ConsoleReader consoleReader = new ConsoleReader();

            consoleReader.setPrompt("chose number inside " + choseRange.toString() + " : ");

            String line;
            while ((line = consoleReader.readLine()) != null) {
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
            e.printStackTrace();
        }


        return Optional.empty(); //TODO implement
    }
}
