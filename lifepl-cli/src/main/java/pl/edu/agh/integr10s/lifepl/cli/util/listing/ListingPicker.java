package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import org.apache.commons.lang.math.IntRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

class ListingPicker<T> {

    private static final Logger logger = LoggerFactory.getLogger(ListingPicker.class);

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
