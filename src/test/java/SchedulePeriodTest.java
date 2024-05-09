import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.SchedulePeriod;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulePeriodTest {

    @Test
    public void getIsTimeSlotTest() {
        Assertions.assertTrue(SchedulePeriod._08H00_09H30.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._09H30_11H00.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._11H00_12H30.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._13H00_14H30.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._14H30_16H00.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._16H00_17H30.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._18H00_19H30.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._19H30_21H00.getIsTimeSlot());
        Assertions.assertTrue(SchedulePeriod._21H00_22H30.getIsTimeSlot());

        Assertions.assertFalse(SchedulePeriod.MANHA.getIsTimeSlot());
        Assertions.assertFalse(SchedulePeriod.TARDE.getIsTimeSlot());
        Assertions.assertFalse(SchedulePeriod.NOITE.getIsTimeSlot());

        Assertions.assertFalse(SchedulePeriod.SEGUNDA_FEIRA.getIsTimeSlot());
        Assertions.assertFalse(SchedulePeriod.TERCA_FEIRA.getIsTimeSlot());
        Assertions.assertFalse(SchedulePeriod.QUARTA_FEIRA.getIsTimeSlot());
        Assertions.assertFalse(SchedulePeriod.QUINTA_FEIRA.getIsTimeSlot());
        Assertions.assertFalse(SchedulePeriod.SEXTA_FEIRA.getIsTimeSlot());
    }

    @Test
    public void getIsTimePeriodTest() {
        Assertions.assertFalse(SchedulePeriod._08H00_09H30.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._09H30_11H00.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._11H00_12H30.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._13H00_14H30.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._14H30_16H00.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._16H00_17H30.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._18H00_19H30.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._19H30_21H00.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod._21H00_22H30.getIsTimePeriod());

        Assertions.assertTrue(SchedulePeriod.MANHA.getIsTimePeriod());
        Assertions.assertTrue(SchedulePeriod.TARDE.getIsTimePeriod());
        Assertions.assertTrue(SchedulePeriod.NOITE.getIsTimePeriod());

        Assertions.assertFalse(SchedulePeriod.SEGUNDA_FEIRA.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod.TERCA_FEIRA.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod.QUARTA_FEIRA.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod.QUINTA_FEIRA.getIsTimePeriod());
        Assertions.assertFalse(SchedulePeriod.SEXTA_FEIRA.getIsTimePeriod());
    }

    @Test
    public void getIsWeekDayTest() {
        Assertions.assertFalse(SchedulePeriod._08H00_09H30.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._09H30_11H00.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._11H00_12H30.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._13H00_14H30.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._14H30_16H00.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._16H00_17H30.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._18H00_19H30.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._19H30_21H00.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod._21H00_22H30.getIsWeekDay());

        Assertions.assertFalse(SchedulePeriod.MANHA.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod.TARDE.getIsWeekDay());
        Assertions.assertFalse(SchedulePeriod.NOITE.getIsWeekDay());

        Assertions.assertTrue(SchedulePeriod.SEGUNDA_FEIRA.getIsWeekDay());
        Assertions.assertTrue(SchedulePeriod.TERCA_FEIRA.getIsWeekDay());
        Assertions.assertTrue(SchedulePeriod.QUARTA_FEIRA.getIsWeekDay());
        Assertions.assertTrue(SchedulePeriod.QUINTA_FEIRA.getIsWeekDay());
        Assertions.assertTrue(SchedulePeriod.SEXTA_FEIRA.getIsWeekDay());
    }

    @Test
    public void getStartTimeTest() {
        Assertions.assertEquals(LocalTime.parse("08:00:00"), SchedulePeriod._08H00_09H30.getStartTime());
        Assertions.assertEquals(LocalTime.parse("09:30:00"), SchedulePeriod._09H30_11H00.getStartTime());
        Assertions.assertEquals(LocalTime.parse("11:00:00"), SchedulePeriod._11H00_12H30.getStartTime());
        Assertions.assertEquals(LocalTime.parse("13:00:00"), SchedulePeriod._13H00_14H30.getStartTime());
        Assertions.assertEquals(LocalTime.parse("14:30:00"), SchedulePeriod._14H30_16H00.getStartTime());
        Assertions.assertEquals(LocalTime.parse("16:00:00"), SchedulePeriod._16H00_17H30.getStartTime());
        Assertions.assertEquals(LocalTime.parse("18:00:00"), SchedulePeriod._18H00_19H30.getStartTime());
        Assertions.assertEquals(LocalTime.parse("19:30:00"), SchedulePeriod._19H30_21H00.getStartTime());
        Assertions.assertEquals(LocalTime.parse("21:00:00"), SchedulePeriod._21H00_22H30.getStartTime());

        Assertions.assertEquals(LocalTime.parse("08:00:00"), SchedulePeriod.MANHA.getStartTime());
        Assertions.assertEquals(LocalTime.parse("13:00:00"), SchedulePeriod.TARDE.getStartTime());
        Assertions.assertEquals(LocalTime.parse("18:00:00"), SchedulePeriod.NOITE.getStartTime());

        Assertions.assertNull(SchedulePeriod.SEGUNDA_FEIRA.getStartTime());
        Assertions.assertNull(SchedulePeriod.TERCA_FEIRA.getStartTime());
        Assertions.assertNull(SchedulePeriod.QUARTA_FEIRA.getStartTime());
        Assertions.assertNull(SchedulePeriod.QUINTA_FEIRA.getStartTime());
        Assertions.assertNull(SchedulePeriod.SEXTA_FEIRA.getStartTime());
    }

    @Test
    public void getEndTimeTest() {
        Assertions.assertEquals(LocalTime.parse("09:30:00"), SchedulePeriod._08H00_09H30.getEndTime());
        Assertions.assertEquals(LocalTime.parse("11:00:00"), SchedulePeriod._09H30_11H00.getEndTime());
        Assertions.assertEquals(LocalTime.parse("12:30:00"), SchedulePeriod._11H00_12H30.getEndTime());
        Assertions.assertEquals(LocalTime.parse("14:30:00"), SchedulePeriod._13H00_14H30.getEndTime());
        Assertions.assertEquals(LocalTime.parse("16:00:00"), SchedulePeriod._14H30_16H00.getEndTime());
        Assertions.assertEquals(LocalTime.parse("17:30:00"), SchedulePeriod._16H00_17H30.getEndTime());
        Assertions.assertEquals(LocalTime.parse("19:30:00"), SchedulePeriod._18H00_19H30.getEndTime());
        Assertions.assertEquals(LocalTime.parse("21:00:00"), SchedulePeriod._19H30_21H00.getEndTime());
        Assertions.assertEquals(LocalTime.parse("22:30:00"), SchedulePeriod._21H00_22H30.getEndTime());

        Assertions.assertEquals(LocalTime.parse("12:30:00"), SchedulePeriod.MANHA.getEndTime());
        Assertions.assertEquals(LocalTime.parse("17:30:00"), SchedulePeriod.TARDE.getEndTime());
        Assertions.assertEquals(LocalTime.parse("22:30:00"), SchedulePeriod.NOITE.getEndTime());

        Assertions.assertNull(SchedulePeriod.SEGUNDA_FEIRA.getEndTime());
        Assertions.assertNull(SchedulePeriod.TERCA_FEIRA.getEndTime());
        Assertions.assertNull(SchedulePeriod.QUARTA_FEIRA.getEndTime());
        Assertions.assertNull(SchedulePeriod.QUINTA_FEIRA.getEndTime());
        Assertions.assertNull(SchedulePeriod.SEXTA_FEIRA.getEndTime());
    }

    @Test
    public void getTimeSlotListTest() {
        Assertions.assertNull(SchedulePeriod._08H00_09H30.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._09H30_11H00.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._11H00_12H30.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._13H00_14H30.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._14H30_16H00.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._16H00_17H30.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._18H00_19H30.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._19H30_21H00.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod._21H00_22H30.getTimeSlotList());

        Assertions.assertEquals(List.of(SchedulePeriod._08H00_09H30, SchedulePeriod._09H30_11H00, SchedulePeriod._11H00_12H30), SchedulePeriod.MANHA.getTimeSlotList());
        Assertions.assertEquals(List.of(SchedulePeriod._13H00_14H30, SchedulePeriod._14H30_16H00, SchedulePeriod._16H00_17H30), SchedulePeriod.TARDE.getTimeSlotList());
        Assertions.assertEquals(List.of(SchedulePeriod._18H00_19H30, SchedulePeriod._19H30_21H00, SchedulePeriod._21H00_22H30), SchedulePeriod.NOITE.getTimeSlotList());

        Assertions.assertNull(SchedulePeriod.SEGUNDA_FEIRA.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod.TERCA_FEIRA.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod.QUARTA_FEIRA.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod.QUINTA_FEIRA.getTimeSlotList());
        Assertions.assertNull(SchedulePeriod.SEXTA_FEIRA.getTimeSlotList());
    }

    @Test
    public void getPreferredDayTest() {
        Assertions.assertNull(SchedulePeriod._08H00_09H30.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._09H30_11H00.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._11H00_12H30.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._13H00_14H30.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._14H30_16H00.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._16H00_17H30.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._18H00_19H30.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._19H30_21H00.getPreferredDay());
        Assertions.assertNull(SchedulePeriod._21H00_22H30.getPreferredDay());

        Assertions.assertNull(SchedulePeriod.MANHA.getPreferredDay());
        Assertions.assertNull(SchedulePeriod.TARDE.getPreferredDay());
        Assertions.assertNull(SchedulePeriod.NOITE.getPreferredDay());

        Assertions.assertEquals(DayOfWeek.MONDAY, SchedulePeriod.SEGUNDA_FEIRA.getPreferredDay());
        Assertions.assertEquals(DayOfWeek.TUESDAY, SchedulePeriod.TERCA_FEIRA.getPreferredDay());
        Assertions.assertEquals(DayOfWeek.WEDNESDAY, SchedulePeriod.QUARTA_FEIRA.getPreferredDay());
        Assertions.assertEquals(DayOfWeek.THURSDAY, SchedulePeriod.QUINTA_FEIRA.getPreferredDay());
        Assertions.assertEquals(DayOfWeek.FRIDAY, SchedulePeriod.SEXTA_FEIRA.getPreferredDay());
    }

    @Test
    public void getAllTimeSlotsTest() {
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(SchedulePeriod._08H00_09H30,
                SchedulePeriod._09H30_11H00, SchedulePeriod._11H00_12H30, SchedulePeriod._13H00_14H30,
                SchedulePeriod._14H30_16H00, SchedulePeriod._16H00_17H30, SchedulePeriod._18H00_19H30,
                SchedulePeriod._19H30_21H00, SchedulePeriod._21H00_22H30)), SchedulePeriod.getAllTimeSlots());
    }

    @Test
    public void getAllTimePeriodsTest() {
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(SchedulePeriod.MANHA, SchedulePeriod.TARDE,
                SchedulePeriod.NOITE)), SchedulePeriod.getAllTimePeriods());
    }

    @Test
    public void getAllWeekDaysTest() {
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(SchedulePeriod.SEGUNDA_FEIRA, SchedulePeriod.TERCA_FEIRA,
                SchedulePeriod.QUARTA_FEIRA, SchedulePeriod.QUINTA_FEIRA, SchedulePeriod.SEXTA_FEIRA)),
                SchedulePeriod.getAllWeekDays());
    }

    @Test
    public void compareToTest() {
        Assertions.assertEquals(-1, SchedulePeriod._08H00_09H30.compareTo(SchedulePeriod._09H30_11H00));
        Assertions.assertEquals(1, SchedulePeriod._09H30_11H00.compareTo(SchedulePeriod._08H00_09H30));
        Assertions.assertEquals(0, SchedulePeriod._08H00_09H30.compareTo(SchedulePeriod._08H00_09H30));

        Assertions.assertEquals(-1, SchedulePeriod.MANHA.compareTo(SchedulePeriod.TARDE));
        Assertions.assertEquals(1, SchedulePeriod.NOITE.compareTo(SchedulePeriod.MANHA));
        Assertions.assertEquals(0, SchedulePeriod.TARDE.compareTo(SchedulePeriod.TARDE));

        Assertions.assertEquals(-1, SchedulePeriod.SEGUNDA_FEIRA.compareTo(SchedulePeriod.TERCA_FEIRA));
        Assertions.assertEquals(1, SchedulePeriod.SEXTA_FEIRA.compareTo(SchedulePeriod.QUINTA_FEIRA));
        Assertions.assertEquals(0, SchedulePeriod.QUARTA_FEIRA.compareTo(SchedulePeriod.QUARTA_FEIRA));
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("08:00 09:30", SchedulePeriod._08H00_09H30.toString());
        Assertions.assertEquals("Período: 08:00 - 12:30", SchedulePeriod.MANHA.toString());
        Assertions.assertEquals("Seg", SchedulePeriod.SEGUNDA_FEIRA.toString());
    }

}
