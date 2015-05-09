package pl.edu.agh.integr10s.lifepl.cli.util.listing;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;

class ListingDataModel<T> extends AbstractTableModel {

    private final String columnNames[];
    private final Object data[][];
    private final ArrayList<T> elements;

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public T getElement(int rowIdx) {
        return elements.get(rowIdx - 1);
    }

    public ListingDataModel(Collection<T> elements, PropertyExtractor<T> propertyExtractor) {
        this.columnNames = new String[propertyExtractor.getPropertyNames().size()];
        this.data = new Object[elements.size()][this.columnNames.length];
        this.elements = new ArrayList<>(elements);

        for (int i = 0; i < propertyExtractor.getPropertyNames().size(); i++) {
            this.columnNames[i] = propertyExtractor.getPropertyNames().get(i);
        }

        int row = 0;
        for (T element : elements) {
            int column = 0;
            for (String columnName : this.columnNames) {
                this.data[row][column] = propertyExtractor.getPropertyValue(columnName, element);
                column++;
            }
            row++;
        }
    }
}
