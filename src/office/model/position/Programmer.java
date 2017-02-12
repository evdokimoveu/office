
package office.model.position;

import java.util.logging.Level;
import java.util.logging.Logger;
import office.model.Position;
import office.model.Task;

/**
 *
 * @author EvdokimovEU
 */
public class Programmer implements Position {
    
    public static final String NAME = "programmer";
    
    private final int wageRate;
    private int hoursWorked;
    private Task task;

    public Programmer(int wageRate) {
        this.wageRate = wageRate;
        this.hoursWorked = 0;
    }
    
    @Override
    public Task call() {
        try {
            task.setDeadline(hoursWorked);            
            task.setWageRate(wageRate);
            Thread.sleep(hoursWorked);
        } catch (InterruptedException ex) {
            Logger.getLogger(Designer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return task;
    }

    @Override
    public void setTask(Task task) {
        this.task = task;
    }
    
    @Override
    public Task getTask() {
        return task;
    }
        
}
