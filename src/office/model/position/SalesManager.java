
package office.model.position;

import office.model.Position;
import office.model.Task;

/**
 *
 * @author EvdokimovEU
 */
public class SalesManager implements Position{
    
    public static final String NAME = "SalesManager";
    
    private final int wageRate;
    private Task task;

    public SalesManager(int wageRate) {
        this.wageRate = wageRate;
    }

    @Override
    public Task call() throws Exception {
        task.setWageRate(wageRate);
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
