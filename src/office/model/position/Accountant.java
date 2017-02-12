
package office.model.position;

import office.model.Position;
import office.model.Task;

/**
 *
 * @author EvdokimovEU
 */
public class Accountant implements Position{
    
    public static final String NAME = "Accountant";
    
    private int wageRate;

    public Accountant(int wageRate) {
        this.wageRate = wageRate;
    }
    
    public void createOrder(){
    
    }
    
    public void assessmentSalary(){
    
    }

    @Override
    public void setTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Task getTask() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Task call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
}
