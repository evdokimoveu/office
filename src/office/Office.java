
package office;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import office.model.Emploee;
import office.model.Task;
import office.model.position.Accountant;
import office.timesheet.DefaultTimeSheet;

/**
 *
 * @author EvdokimovEU
 */
public class Office implements Emploee.NewTaskCallBack {
    
    public static final List<Task> NEW_TASK = Collections.synchronizedList(new ArrayList<>());
    //public static final List<Task> COMPLETED_TASK = Collections.synchronizedList(new ArrayList<>());
    
    private Accountant accountant;
    private List<Emploee> emploees;
    private final List<Task> freelance;
    private final Calendar calendar;
    private int currentDay;

    public Office() {
        this.calendar = Calendar.getInstance();
        this.freelance = new ArrayList<>();
        createEmloee();
        startWork();
    }  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Office();
    }

    
    @Override
    public synchronized void newTaskCall(Task task) {        
        NEW_TASK.add(task);
        for(int i = 0; i < emploees.size(); i++){
            if(emploees.get(i).isFree() && emploees.get(i).isPositionExists(task.getPositionName())){
                emploees.get(i).setFree(false);
                emploees.get(i).startTask(task);
                break;
            }
            else{
                if(i == emploees.size() - 1){
                    freelance.add(task);
                }
            }
        }
    }
    
    private void createEmloee() {
        Random r = new Random();
        int emploeeCount = r.nextInt(90) + 9;
        emploees = new ArrayList<>();
        
        //Create Emploee with director and Sales Manager
        emploees.add(new Emploee(this, "Main Anonymous", new DefaultTimeSheet(), true, true));
        
        for(int i = 0; i < emploeeCount; i++){
            emploees.add(new Emploee(this, "Anonymous " + i, new DefaultTimeSheet(), false, false));
        }
        currentDay = 1;
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
    }
    
    private void startWork(){        
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            for(int i = 0; i < emploees.size(); i++){
                emploees.get(i).start(currentDay);
            }        
            ++currentDay;
            calendar.set(Calendar.DAY_OF_MONTH, currentDay);
        }, 0, 1, TimeUnit.DAYS);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
            accountant.assessmentSalary(emploees);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            try {
                accountant.createReport(emploees, freelance);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Office.class.getName()).log(Level.SEVERE, null, ex);
            }
            executorService.shutdown();
        }        
    }
}
