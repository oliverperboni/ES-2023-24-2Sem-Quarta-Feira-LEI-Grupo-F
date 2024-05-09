import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.ScheduleInstant;
import structures.SchedulePeriod;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class ScheduleInstantTest {

    @Test
    public void getScheduleDateTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));

        ScheduleInstant testInstant = new ScheduleInstant(testDate, testPeriod);

        assertEquals(testInstant.getScheduleDate(), testDate);
    }

    @Test
    public void getScheduleTimeTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));

        ScheduleInstant testInstant = new ScheduleInstant(testDate, testPeriod);

        assertEquals(testInstant.getScheduleTime(), testPeriod);
    }

    @Test
    public void setScheduleDateTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));

        ScheduleInstant testInstant = new ScheduleInstant(testDate, testPeriod);
        testInstant.setScheduleDate(LocalDate.parse("02/01/2022", formatter));

        assertEquals(testInstant.getScheduleDate(), LocalDate.parse("02/01/2022", formatter));
    }

    @Test
    public void setScheduleTimeTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));

        ScheduleInstant testInstant = new ScheduleInstant(testDate, testPeriod);
        testInstant.setScheduleTime(new SchedulePeriod(LocalTime.parse("09:30:00"), LocalTime.parse("11:00:00")));

        assertEquals(testInstant.getScheduleTime().compareTo(new SchedulePeriod(LocalTime.parse("09:30:00"), LocalTime.parse("11:00:00"))), 0);
    }

    @Test
    public void compareToTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate testDate1 = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod1 = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));
        ScheduleInstant testInstant1 = new ScheduleInstant(testDate1, testPeriod1);

        LocalDate testDate2 = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod2 = new SchedulePeriod(LocalTime.parse("09:30:00"), LocalTime.parse("11:00:00"));
        ScheduleInstant testInstant2 = new ScheduleInstant(testDate2, testPeriod2);

        LocalDate testDate3 = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod3 = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));
        ScheduleInstant testInstant3 = new ScheduleInstant(testDate3, testPeriod3);

        Assertions.assertEquals(-1, testInstant1.compareTo(testInstant2));
        Assertions.assertEquals(1, testInstant2.compareTo(testInstant1));
        Assertions.assertEquals(0, testInstant1.compareTo(testInstant3));

    }

    @Test
    public void toStringTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));

        ScheduleInstant testInstant = new ScheduleInstant(testDate, testPeriod);
        Assertions.assertEquals("08:00 09:30 2022-01-01", testInstant.toString());
    }

    @Test
    public void weekDayToStringTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse("01/01/2022", formatter);
        SchedulePeriod testPeriod = new SchedulePeriod(LocalTime.parse("08:00:00"), LocalTime.parse("09:30:00"));

        ScheduleInstant testInstant = new ScheduleInstant(testDate, testPeriod);
        Assertions.assertEquals("Sáb", testInstant.weekDayToString());
    }


}
