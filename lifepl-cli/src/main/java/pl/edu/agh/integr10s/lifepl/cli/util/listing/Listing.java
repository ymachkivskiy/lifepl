package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import dnl.utils.text.table.TextTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public class Listing<T> {

    private static final Logger logger = LoggerFactory.getLogger(Listing.class);

    private final ListingDataModel<T> model;
    private final TextTable table;
    private final ListingPicker<T> picker;

    private Listing(ListingDataModel<T> model) {
        this.model = model;
        this.table = new TextTable(model, true);
        this.picker = new ListingPicker<>();
    }

    public static <T> Listing<T> For(Collection<T> elements, PropertyExtractor<T> propertyExtractor) {
        return new Listing<>(new ListingDataModel<>(elements, propertyExtractor));
    }

    public Optional<T> choose() {
        list();
        return picker.peekOne(model);
    }

    public void list() {
        printTable();
        System.out.println();
    }

    private void printTable() {
        table.printTable();
    }
}
