
package office.model.position;

import java.math.BigDecimal;
import java.util.Random;
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
            Random random = new Random();            
            hoursWorked = random.nextInt(3600 * 1000) + 3600 * 1000;
            double deadline = BigDecimal.valueOf(hoursWorked / (3600 * 1000.0)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
            task.setDeadline(deadline);
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
