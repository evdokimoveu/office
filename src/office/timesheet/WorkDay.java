
package office.timesheet;

/**
 *
 * @author EvdokimovEU
 */
public class WorkDay {
    private int dayNumber;
    private int duration;//Default value 8 * 60 minute
    private boolean weekday;

    public WorkDay(int dayNumber, int duration, boolean weekday) {
        this.dayNumber = dayNumber;
        this.duration = duration;
        this.weekday = weekday;
    }
    
    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isWeekday() {
        return weekday;
    }

    public void setWeekday(boolean weekday) {
        this.weekday = weekday;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.dayNumber;
        hash = 17 * hash + this.duration;
        hash = 17 * hash + (this.weekday ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkDay other = (WorkDay) obj;
        if (this.dayNumber != other.dayNumber) {
            return false;
        }
        if (this.duration != other.duration) {
            return false;
        }
        if (this.weekday != other.weekday) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WorkDay{" + "dayNumber=" + dayNumber + ", duration=" + duration + ", weekday=" + weekday + '}';
    }
}
