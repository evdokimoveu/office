
package office.model.position;

import java.util.ArrayList;
import java.util.List;
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
    private final int limit;
    
    public Director(int wageRate, int limit) {
        this.wageRate = wageRate;
        if(limit > 1){
            Random random = new Random();
            this.limit = random.nextInt(limit - 1) + 1;
        }
        else{
            this.limit = 1;
        }
    }
    
    public List<Task> makeTask(){
        Random random = new Random();
        List<Task> tasks = new ArrayList<>();
        for(int j = 0; j < limit; j++){
            int i = random.nextInt(Task.values().length);
            tasks.add(Task.values()[i]);
        }
        return tasks;
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
