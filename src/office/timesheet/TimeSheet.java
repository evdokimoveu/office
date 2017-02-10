
package office.timesheet;

/**
 *
 * @author JenDOS
 */
public interface TimeSheet {
    void updateDay(Integer dayNumber, Integer duration, Boolean weekday);
    WorkDay getWorkDay(int day);
}
