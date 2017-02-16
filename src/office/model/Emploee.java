
package office.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import office.Office;
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
    
    private final String name;
    private Boolean free;
    private final boolean createDirector;
    private final boolean createManager;
    private int dayNumber;
    private WorkDay day;
    private final long startTime;
    private final List<Task> tasksCompleted;
    private final List<BigDecimal> salaries;
    private Map<String, Position> positions;
    private final TimeSheet timeSheet;
    private final NewTaskCallBack newTaskCallBack;
    

    /**
     * 
     * @param office
     * @param name
     * @param timeSheet
     * @param createDirector
     * @param createManager
     */
    public Emploee(Office office, String name, TimeSheet timeSheet, Boolean createDirector, Boolean createManager) {
        this.timeSheet = timeSheet;        
        this.createDirector = createDirector;
        this.createManager = createManager;
        this.free = true;
        this.newTaskCallBack = office;
        this.startTime = System.currentTimeMillis();
        this.name = name;
        this.tasksCompleted = new ArrayList<>();
        this.salaries = new ArrayList<>();
        createPosition();
    }
    
    public void start(int dayNumber){
        this.dayNumber = dayNumber;
        this.day = timeSheet.getWorkDay(this.dayNumber);        
        Timer timer = new Timer();
        timer.schedule(new DirectorRun(), 0);
        if(System.currentTimeMillis() - startTime > day.getDuration()){
            timer.cancel();
        }        
    }
    
    public void startTask(Task task){
        Timer timer = new Timer();
        timer.schedule(new TaskRun(task), 0);
        if(System.currentTimeMillis() - startTime > day.getDuration()){
            timer.cancel();
        }
    }

    public Set<String> getPositionName(){
        return positions.keySet();
    }   

    public Map<String, Position> getPositions() {
        return positions;
    }    
    
    public Boolean isFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
    
    public boolean isPositionExists(String positionName){
        return positions.containsKey(positionName);        
    }

    public List<BigDecimal> getSalaries() {
        return salaries;
    }

    public void addSalaries(BigDecimal salary) {
        salaries.add(salary);
    }
    
    public String getName() {
        return name;
    }
    
    public List<Task> getTasksCompleted() {
        return tasksCompleted;
    }    
    
    private void createPosition(){
        positions = new HashMap<>();
        Random random = new Random();
        int positionCount = random.nextInt(4) + 1;        
        Task[] t = Task.values();
        List<String> pos = new ArrayList<>();
        for (Task t1 : t) {
            pos.add(t1.getPositionName());
        }
        pos.add(Director.NAME);
        if(createDirector){
            positions.put(Director.NAME, getPosition(Director.NAME));
        }
        if(createManager){
            positions.put(SalesManager.NAME, getPosition(SalesManager.NAME));
        }
        for(int i = 0; i < positionCount; i++){
            String positionName = pos.get(random.nextInt(5));
            positions.put(positionName, getPosition(positionName));
        }        
    }

    private class DirectorRun extends TimerTask {
               
        @Override
        public void run() {            
            if(positions.containsKey(Director.NAME)){
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(() -> {                    
                    if(System.currentTimeMillis() - startTime > day.getDuration()){
                        executorService.shutdown();
                    }
                    if(free){
                        free = false;
                        Director director = (Director) positions.get(Director.NAME);
                        List<Task> tasks = director.makeTask();                        
                        for(Task task : tasks){
                            newTaskCallBack.newTaskCall(task);
                        }
                        free = true;
                    }                    
                }, 0, 3600, TimeUnit.MILLISECONDS);
            }
        }
    }
    
    public class TaskRun extends TimerTask {

        private final Task task;

        public TaskRun(Task task) {
            this.task = task;
        }
        
        @Override
        public void run() {
                        
            free = false;
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
            free = true;
        }
        
        private Task doTask(Position position, Task task){            
            position.setTask(task);
            ExecutorService exService = Executors.newSingleThreadExecutor();
            Future<Task> callFuture = exService.submit(position);
            Task taskCompleted = null;
            try {
                taskCompleted = callFuture.get();
                exService.awaitTermination(3, TimeUnit.SECONDS);
                exService.shutdownNow();
                tasksCompleted.add(taskCompleted);
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Emploee.class.getName()).log(Level.SEVERE, null, ex);
            }        
            return taskCompleted;
        }
    }
    
    //Create new Position object
    private Position getPosition(String positionName){        
        if(positionName.equals(Programmer.NAME)){
            return new Programmer(15);
        }
        else if(positionName.equals(Designer.NAME)){
            return new Designer(13);
        }        
        else if(positionName.equals(Director.NAME)){
            return new Director(2000, 1);
        }
        else if(positionName.equals(SalesManager.NAME)){
            return new SalesManager(1500);
        }
        else if(positionName.equals(Tester.NAME)){
            return new Tester(14);
        }
        else{
            throw new IllegalArgumentException("Position name : " +  positionName + " doesn't exists");
        }
    }
    
    public interface NewTaskCallBack {
        void newTaskCall(Task task);
    }
}
