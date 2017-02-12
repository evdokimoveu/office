
package office.model.position;

import java.util.Random;
import office.model.Position;
import office.model.Task;

/**
 *
 * @author EvdokimovEU
 */
public class Director implements Position {
    
    public static final String NAME = "Director";
    
    private int wageRate;
    
    public Director(int wageRate) {
        this.wageRate = wageRate;
    }
    
    public Task makeTask(){
        Random random = new Random();
        int i = random.nextInt(Task.values().length - 1);        
        return Task.values()[i];
    }

    @Override
    public Task call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getWageRate() {
        return wageRate;
    }

    public void setWageRate(int wageRate) {
        this.wageRate = wageRate;
    }

    @Override
    public void setTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Task getTask() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
