
package office.model.position;

import office.model.Position;

/**
 *
 * @author EvdokimovEU
 */
public class Programmer implements Position {
    
    public static final String NAME = "programmer";
    
    private int wageRate;
    private int hoursWorked;

    public Programmer(int wageRate) {
        this.wageRate = wageRate;
        this.hoursWorked = 0;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
        
    public void doTask(){
    }

   
}
