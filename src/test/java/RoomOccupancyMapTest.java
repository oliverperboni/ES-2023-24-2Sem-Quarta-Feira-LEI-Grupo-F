import org.junit.jupiter.api.Test;
import core.RoomOccupancyMap;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomOccupancyMapTest {


    @Test
    void convertDayOfWeekToInt_ValidDay_ReturnsCorrectValue() {
        assertEquals(1, RoomOccupancyMap.convertDayOfWeekToInt("Seg"));
        assertEquals(6, RoomOccupancyMap.convertDayOfWeekToInt("Sáb"));
    }

    @Test
    void convertDayOfWeekToInt_InvalidDay_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RoomOccupancyMap.convertDayOfWeekToInt("InvalidDay");
        });
    }

    @Test
    void countOccurrences_NoFilter_ReturnsCorrectCount() {
        List<String[]> testData = Arrays.asList(
                new String[]{"", "", "", "", "", "Seg", "08:00:00", "", "", "", ""},
                new String[]{"", "", "", "", "", "Seg", "08:00:00", "", "", "", ""},
                new String[]{"", "", "", "", "", "Ter", "08:30:00", "", "", "", ""}
        );
        assertEquals(2, RoomOccupancyMap.countOccurrences(testData, 1, 0));
    }

    @Test
    void countOccurrences_WithFilter_ReturnsCorrectCount() {
        List<String[]> testData = Arrays.asList(
                new String[]{"", "", "", "", "", "Seg", "08:00:00", "", "", "", ""},
                new String[]{"", "", "", "", "", "Seg", "08:00:00", "", "", "", ""},
                new String[]{"", "", "", "", "", "Ter", "08:30:00", "", "", "", ""}
        );
        assertEquals(1, RoomOccupancyMap.countOccurrences(testData, 2, 1)); // Filtering for Tuesday and 8:30
    }

    @Test
    void getWeekOfYear_ValidDate_ReturnsCorrectWeek() {
        assertEquals(42, RoomOccupancyMap.getWeekOfYear("20/10/2022"));
    }

    @Test
    void getWeekOfYear_InvalidDate_ReturnsMinusOne() {
        assertEquals(-1, RoomOccupancyMap.getWeekOfYear("invalidDate"));
    }

    @Test
    void convertDateOfDayToInt_ValidTime_ReturnsCorrectValue() {
        assertEquals(0, RoomOccupancyMap.convertDateOfDayToInt("08:00:00"));
        assertEquals(26, RoomOccupancyMap.convertDateOfDayToInt("21:00:00"));
    }

    @Test
    void convertDateOfDayToInt_InvalidTime_ReturnsMinusOne() {
        assertEquals(-1, RoomOccupancyMap.convertDateOfDayToInt("invalidTime"));
    }

    @Test
    void readCSV_ValidFile_ReturnsNonEmptyList() {
        List<String[]> data = RoomOccupancyMap.readCSV("csv/HorarioDeExemplo.csv");
        assertFalse(data.isEmpty());
    }

    @Test
    void readCSV_InvalidFile_ReturnsEmptyList() {
        List<String[]> data = RoomOccupancyMap.readCSV("invalidFile.csv");
        assertTrue(data.isEmpty());
    }

}
