
package office.model.position;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import office.model.Position;
import office.model.Task;

/**
 *
 * @author EvdokimovEU
 */
public class SalesManager implements Position{
    
    public static final String NAME = "SalesManager";
    
    private final int wageRate;
    private int hoursWorked;
    private Task task;

    public SalesManager(int wageRate) {
        this.wageRate = wageRate;
    }

    @Override
    public Task call() throws Exception {
        try {
            Random random = new Random();
            hoursWorked = random.nextInt(3600) + 3600;
            task.setDeadline(0);
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

    public int getWageRate() {
        return wageRate;
    }
    
}
