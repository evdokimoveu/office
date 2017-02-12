
package office.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import office.model.position.Accountant;
import office.model.position.Designer;
import office.model.position.Director;
import office.model.position.Programmer;
import office.model.position.SalesManager;
import office.model.position.Tester;
import office.timesheet.TimeSheet;
import office.timesheet.WorkDay;

/**
 *
 * @author EvdokimovEU
 */
public class Emploee {
    
    private Boolean free;
    private Map<String, Position> positions;
    private final TimeSheet timeSheet;
    private NewTaskCallBack newTaskCallBack;
    private CompletedTaskCallBack completedTaskCallBack;
    private List<Task> tasks;

    public Emploee(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
        this.tasks = Collections.synchronizedList(new ArrayList<>());
        createPosition();
    }
    
    public void start(int dayNumber){
        WorkDay day = timeSheet.getWorkDay(dayNumber);
        Timer timer = new Timer();
        timer.schedule(new EnploeeRun(), day.getDuration());
    }
    
    public Set<String> getPositionName(){
        return positions.keySet();
    }   

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    private void createPosition(){
        positions = new HashMap<>();
    }
    
    private class EnploeeRun extends TimerTask {

        @Override
        public void run() {
            if(positions.containsKey(Director.NAME)){
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(() -> {
                    if(free){
                        free = false;
                        Director director = (Director) positions.get(Director.NAME);
                        newTaskCallBack.newTaskCall(director.makeTask());
                        free = true;
                    }
                }, 0, 1, TimeUnit.HOURS);                
            }            
            if(positions.containsKey(Accountant.NAME)){
                if(free){
                    free = false;
                    Accountant accountant = (Accountant) positions.get(Accountant.NAME);
                    accountant.assessmentSalary();
                    accountant.createOrder();
                    free = true;
                }                
            }
            if(tasks.size() > 0){
                free = false;
                Task task = tasks.get(0);
                String position = task.getPositionName();
                if(position.equals(Programmer.NAME)){
                    Programmer programmer = (Programmer) positions.get(Programmer.NAME);
                    doTask(programmer, task);
                }
                if(position.equals(Designer.NAME)){
                    Designer designer = (Designer) positions.get(Designer.NAME);
                    doTask(designer, task);
                }
                if(position.equals(SalesManager.NAME)){
                    SalesManager salesManager = (SalesManager) positions.get(SalesManager.NAME);                    
                    doTask(salesManager, task);
                }
                if(position.equals(Tester.NAME)){
                    Tester tester = (Tester) positions.get(Tester.NAME);
                    doTask(tester, task);
                }                
                tasks.clear();
                free = true;
            }
        }
    }
    
    private Task doTask(Position position, Task task){
        position.setTask(task);
        ExecutorService exService = Executors.newSingleThreadExecutor();
        Future<Task> callFuture = exService.submit(position);
        Task tasksCompleted = null;
        try {
            tasksCompleted = callFuture.get();
            exService.awaitTermination(30, TimeUnit.SECONDS);
            exService.shutdownNow();
            completedTaskCallBack.completedTaskCall(tasksCompleted);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Emploee.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return tasksCompleted;
    }
    
    public interface NewTaskCallBack {
        void newTaskCall(Task task);
    }

    public interface CompletedTaskCallBack {
        void completedTaskCall(Task task);
    }
}
