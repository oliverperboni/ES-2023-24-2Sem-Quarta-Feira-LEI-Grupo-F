import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnSortTest {

    @Test
    public void addColumnSortingTest() throws IOException {
        // Table instance using a schedule .csv file
        Table table = new Table("csv/HorarioDeExemplo.csv", false);

        // Sorter associated with the Table instance's JTable
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getJTable().getRowSorter();

        // Get the current sort keys
        java.util.List<? extends RowSorter.SortKey> sortKeys = sorter.getSortKeys();

        // Check that the JTable sorter has immediate sorting after update enabled
        assertTrue(sorter.getSortsOnUpdates());

        // Verify that the expected SortKeys are present (13, for this file)
        assertEquals(13, sortKeys.size());

        // Check that the 1st column is sorted
        assertEquals(0, sortKeys.getFirst().getColumn());

        // Check ascending sort order, as defined in the addColumnSorting() method
        assertEquals(SortOrder.ASCENDING, sortKeys.getFirst().getSortOrder());
    }

}
