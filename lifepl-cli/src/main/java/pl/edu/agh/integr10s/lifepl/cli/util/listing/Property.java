package pl.edu.agh.integr10s.lifepl.cli.util.listing;

public abstract class Property<T> {

    private final String name;
    private final int sortKey;

    public Property(String name) {
        this(name, 0);
    }

    public Property(String name, int sortKey) {
        this.name = name;
        this.sortKey = sortKey;
    }

    public int getSortKey() {
        return sortKey;
    }

    public String getName() {
        return this.name;
    }

    public abstract Object extract(T propertyHost);

    @Override
    public int hashCode() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.cli.util.listing.Property:: int hashCode ()
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO implement override pl.edu.agh.integr10s.lifepl.cli.util.listing.Property:: boolean equals ()
        return super.equals(obj);
    }

    @Override
    public String toString() {
        //TODO implement override pl.edu.agh.integr10s.lifepl.cli.util.listing.Property:: String toString ()
        return super.toString();
    }
}
