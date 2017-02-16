
package office.timesheet;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author EvdokimovEU
 */
public class DefaultTimeSheet implements TimeSheet{
    
    private Map<Integer, WorkDay> days;    
    private Calendar calendar;
    private int duration;

    public DefaultTimeSheet() {
        this(8 * 3600);
    }

    public DefaultTimeSheet(int duration) {        
        this.calendar = Calendar.getInstance();
        if(duration < 24 * 3600){
            this.duration = duration;
        }
        else{
            throw new IllegalArgumentException("Duration can not be more than 24 hours");
        }
        create();
    }
    
    @Override
    public void updateDay(Integer dayNumber, Integer duration, Boolean weekday){
        if(dayNumber != null){
            WorkDay workDay = days.get(dayNumber);
            if(workDay != null){
                if(duration != null && duration < 24 * 3600){
                    workDay.setDuration(duration);
                }
                if(weekday != null){
                    workDay.setWeekday(weekday);
                }
                days.put(dayNumber, workDay);
            }
        }
    }
    
    @Override
    public WorkDay getWorkDay(int day) {
        return days.get(day);
    }
    
    //Create default duration
    private void create(){        
        days = new HashMap<>();
        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        boolean weekday;
        for(int i = 1; i <= dayCount; i++){
            calendar.set(Calendar.DAY_OF_MONTH, i);
            WorkDay workDay = null;
            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY 
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                weekday = false;
                workDay = new WorkDay(i, 0, weekday);
            }
            else{
                weekday = true;
                workDay = new WorkDay(i, duration, weekday);
            }
            days.put(i, workDay);
        }
    }
}
